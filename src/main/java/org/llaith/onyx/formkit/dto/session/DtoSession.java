/*
 * Copyright (c) 2016.
 */

package org.llaith.onyx.formkit.dto.session;


import com.google.common.base.Supplier;
import org.apache.commons.lang3.ObjectUtils;
import org.llaith.onyx.formkit.dto.Dto;
import org.llaith.onyx.formkit.dto.DtoCollection;
import org.llaith.onyx.formkit.dto.DtoField;
import org.llaith.onyx.toolkit.exception.UncheckedException;
import org.llaith.onyx.toolkit.lang.PersistentIdentities.BasePersistentIdentity;
import org.llaith.onyx.toolkit.lang.PersistentIdentities.PersistentIdentity;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.llaith.onyx.toolkit.lang.Guard.notNull;
import static org.llaith.onyx.toolkit.lang.PersistentIdentities.equivalenceFields;

/**
 * <p>
 * The DtoSession is used to place built (identified) dto instances to be synchronised with each other.
 * The synchronization occurs on the acceptChanges/cancelChanges invocation, which means it is good practice
 * to call cancelChanges even if you plan to discard the dto, although this is not strictly necessary.
 * </P>
 * <pre>
 *     SomeDto dto1 = session.addDto(dtoBuilder.build());
 *     SomeDto dto2 = session.addDto(dtoBuilder.build());
 *
 *     dto1.setSomeField(value);
 *
 *     Asset.Equals(dto1.getSomeField(),dto2.getSomeField());
 * </pre>
 * <p>
 * IMPORTANT NOTE: because adding dtos needs to be recursive, and i don;t want to reference count, we
 * instead leave them until the session is cleared(). The usage should reflect the fact that the
 * session should be cleared or popped altogether periodically.
 * <p>
 * For this reason the root layer has been changed to not accept objects after construction as they would
 * never be cleared.
 */
public class DtoSession {


    private final Supplier<DtoBus> busFactory;

    private final HashMap<Object,DtoBus> buses = new HashMap<>();

    private final Deque<DtoLayer> layers = new LinkedList<>();

    private final Set<Object> bookmarks = new HashSet<>();

    private final DtoLayer root;

    @PersistentIdentity
    private static class DtoLayer extends BasePersistentIdentity implements Iterable<Dto> {

        @PersistentIdentity
        private final Object bookmark;

        private final Set<Dto> contents = new HashSet<>();

        public DtoLayer(final Object bookmark) {

            this.bookmark = notNull(bookmark);

        }

        public <X extends Dto> X add(final X dto) {

            this.contents.add(dto);

            return dto;

        }

        public boolean contains(final Dto dto) {

            return this.contents.contains(dto);

        }

        public void reset() {

            this.contents.clear();

        }

        @Override
        public Iterator<Dto> iterator() {

            return new HashSet<>(contents).iterator();

        }

    }


    /**
     * Constructs a new DtoSession.
     *
     * @param busFactory a Supplier for creating new EventBus instances for
     *                   each identified Dto.
     */
    public DtoSession(final Supplier<DtoBus> busFactory, final Object bookmark) {

        this.busFactory = busFactory;

        this.root = new DtoLayer(bookmark);

        this.pushLayer(this.root);

    }

    /**
     * Pushes another layer for DtoObject instances onto the stack. It is presumed
     * that each layer represents some sort of 'view' of an application. It is
     * more convenient to discard an entire layer of DtoObjects en-mass then to
     * worry about discarding and un-registering them one by one.
     *
     * @param bookmark the identifier used as the bookmark in order to pop
     *                 back to that point when finished.
     */
    public Object pushLayer(final Object bookmark) {

        if (this.bookmarks.contains(notNull(bookmark))) throw new UncheckedException(String.format(
                "Cannot push layer with duplicate bookmark: %s",
                bookmark));

        this.layers.push(new DtoLayer(bookmark));

        return bookmark;

    }

    public Object popLayer() {

        final DtoLayer layer = this.layers.pop();

        if (this.layers.isEmpty()) {

            this.layers.add(this.root); // add it back

            throw new UncheckedException("Cannot remove root-layer of dto-session");

        }

        return layer.bookmark;

    }

    /**
     * Rewind (pop) the stack back to the given bookmark, un-registering all DtoObjects
     * stored in the popped layers from their EventBuses.
     *
     * @param bookmark the identifier that marks the point at which we want to
     *                 rewind the stack to.
     */
    public Object popToLayer(final Object bookmark) {

        final Set<Object> lhs = new HashSet<>(singletonList(notNull(bookmark)));

        while (ObjectUtils.notEqual(lhs, equivalenceFields(notNull(this.layers.peek())))) {

            this.popLayer();

        }

        return this.popLayer();

    }

    /**
     * Add a DtoObject to the session. A reference to the DtoObject will be stored in the
     * current layer and the DtoObject will be registered against the correct EventBus. If
     * it is the first dto with a given Identity, a new EventBus will be created for it.
     *
     * @param dto the DtoObject to add to the current layer.
     * @param <X> The type of the DtoObject
     * @return the dto object that was passed in (for chaining).
     */
    public <X extends Dto> X addDto(final X dto) {

        // if new, will create a uuid-based identity for it
        if (dto.isNew()) dto.acceptChanges();

        // don't add twice
        if (!this.layers.isEmpty() && this.layers.peek().contains(dto)) return dto;

        this.addDtoToBus(dto);

        this.addDtoToSession(dto);

        this.addNestedToSession(dto);

        this.addChildrenToSession(dto);

        return dto;

    }

    /**
     * Clears all the current layers dtos.
     */
    public void clearDtos() {

        if (this.layers.isEmpty()) return;

        final DtoLayer current = this.layers.peek();

        // to avoid hanging on to references via the bus
        for (final Dto dto : current) {
            this.removeDtoFromBus(dto);
        }

        current.reset();

    }

    private void addNestedToSession(final Dto dto) {

        for (final DtoField field : dto.fields().values()) {
            if (Dto.class.isAssignableFrom(field.type())) {
                // what about the original/new/etc values? do we leave them behind in the session?
                if (dto.has(field.name())) this.addDto((Dto)dto.get(field.name()));
            }
        }

    }

    private void addChildrenToSession(final Dto dto) {

        for (final DtoField field : dto.fields().values()) {
            if (DtoCollection.class.isAssignableFrom(field.type())) {
                // what about the original/new/etc values? do we leave them behind in the session?
                if (dto.has(field.name())) {
                    for (final Dto nested : (DtoCollection)dto.get(field.name())) {
                        this.addDto(nested);
                    }
                }
            }
        }

    }

    private void addDtoToBus(final Dto dto) {
        if (!this.buses.containsKey(dto.identity())) this.buses.put(
                dto.identity(),
                this.busFactory.get());

        dto.register(this.buses.get(dto.identity()));
    }

    private void removeDtoFromBus(final Dto dto) {

        final DtoBus bus = this.buses.get(dto.identity());

        if (bus != null) {

            dto.unregister(bus);

            if (bus.count() < 1) this.buses.remove(dto.identity());
        }
    }

    private void addDtoToSession(final Dto dto) {

        this.layers.peek().add(dto);

    }

}
