/*
 * Copyright (c) 2016.
 */

package org.llaith.onyx.formkit.dto;


import com.google.common.base.MoreObjects;
import org.llaith.onyx.formkit.dto.session.DtoBus;
import org.llaith.onyx.formkit.dto.session.DtoRefreshEvent;
import org.llaith.onyx.toolkit.exception.UncheckedException;
import org.llaith.onyx.toolkit.lang.Guard;
import org.llaith.onyx.toolkit.pattern.meta.MetadataContainer;
import org.llaith.onyx.toolkit.pattern.meta.MetadataDelegate;
import org.llaith.onyx.toolkit.reflection.Primitive;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * <p>
 * NOTE: if you want a non-param ctor (such as for jaxws) you need to make a
 * concrete-baseclass that passes it in. Eg:
 * <pre>
 *                 class SomeObject extends DtoObject {
 *                     public SomeObject() {
 *                         super(DataTypes.SOME_OBJECT);
 *                     }
 *                     // also
 *                     SomeObject getThis() {
 *                         return this;
 *                     }
 *                     ...
 *                 }
 *                 </pre>
 * NOTE ALSO: These are not value objects. Each instance of this class is not
 * equivalent. This is because of the change tracking. However we can compare
 * them with a little more work by using the Identifying field defs.
 * <p>
 * <p>
 * 1
 * a/ eform one is fully dynamic - add support to dataType for adding fields (identity!!!)
 * b/ reconsider FieldInit
 * <p>
 * 2
 * a/ change to get(Class<X>,X)
 * b/ OR change to make id outside of FieldDef.
 */
public final class Dto implements ChangeTracked {

    private final MetadataDelegate metadata = new MetadataDelegate();

    private final String identityName;
    private final Map<String,DtoField> identityIndex = new HashMap<>();

    private final String dtoName;
    private final Map<String,DtoField> valueIndex = new HashMap<>();

    private final Map<String,ChangeTrackedCyclicWrapper> cycleIndex = new HashMap<>();

    private DtoIdentity identity = null;

    private DtoBus bus = null;

    public Dto(final String identityName, final Collection<DtoField> values) {

        this(identityName, identityName, values);

    }

    public Dto(final String identityName, final String dtoName, final Collection<DtoField> values) {

        this.identityName = Guard.notNull(identityName);

        this.dtoName = Guard.notNull(dtoName);

        this.valueIndex.putAll(this.indexValues(values));

        this.cycleIndex.putAll(this.indexCycles(values));

        this.identityIndex.putAll(this.indexIdentity(values));

    }

    private Map<String,DtoField> indexValues(final Collection<DtoField> values) {

        final Map<String,DtoField> index = new HashMap<>();

        for (final DtoField value : values) {
            index.put(
                    value.name(),
                    value);
        }

        return index;
    }

    private Map<String,ChangeTrackedCyclicWrapper> indexCycles(final Collection<DtoField> values) {

        final Map<String,ChangeTrackedCyclicWrapper> index = new HashMap<>();

        for (final DtoField value : values) {
            index.put(
                    value.name(),
                    new ChangeTrackedCyclicWrapper(value));
        }

        return index;
    }

    private Map<String,DtoField> indexIdentity(final Collection<DtoField> values) {

        final Map<String,DtoField> index = new HashMap<>();

        for (final DtoField value : values) {
            if (value.isIdentity()) this.identityIndex.put(
                    value.name(),
                    value);
        }

        return index;
    }

    public boolean isNew() {
        return this.identity == null;
    }

    public DtoIdentity identity() {
        return this.identity;
    }

    public String identityName() {
        return identityName;
    }

    public String dtoName() {
        return dtoName;
    }

    public Map<String,DtoField> fields() {

        final Map<String,DtoField> map = new HashMap<>();

        for (final Entry<String,DtoField> entry : this.valueIndex.entrySet()) {
            map.put(
                    entry.getKey(),
                    entry.getValue());
        }

        return map;
    }

    public boolean has(final String name) {
        return this.dtoFieldFor(name).hasCurrent();
    }

    @SuppressWarnings("unchecked")
    public <X> X get(final String name, final Class<X> klass) {

        if (klass.isPrimitive()) {
            Primitive<?> p = Primitive.primitiveFor(klass);
            if (p != null && p.wrapperClass().isAssignableFrom(this.get(name).getClass())) {
                // Extreme cheating, to mess with boxing, but seems to work anyway due to unboxing!
                return (X)p.wrapperClass().cast(this.get(name));
            }
        }

        return klass.cast(this.get(name));
    }

    public Object get(final String name) {
        return this.dtoFieldFor(name).currentValue();
    }

    public void set(final String name, final Object value) {
        this.dtoFieldFor(name).setValue(value);
    }

    public Dto setThis(final String name, final Object value) {
        this.set(name, value);
        return this;
    }

    public void setValues(final Dto other) {
        for (final String name : this.valueIndex.keySet()) {
            final DtoField val = other.dtoFieldFor(name);
            if (val.isInitialized()) this.set(name, val.currentValue());
        }
    }

    public void setValues(final Map<String,Object> map) {
        for (String id : map.keySet()) {
            this.set(id, map.get(id));
        }
    }

    public void resetValues(final Dto other) {
        for (final String name : this.valueIndex.keySet()) {
            final DtoField val = other.dtoFieldFor(name);
            if (val.isInitialized()) this.reset(name, val.currentValue());
        }
    }

    public void resetValues(final Map<String,Object> map) {
        for (String id : map.keySet()) {
            this.reset(id, map.get(id));
        }
    }

    public void reset(final String name, final Object value) {
        this.dtoFieldFor(name).resetValue(value);
    }

    public Dto resetThis(final String name, final Object value) {
        this.reset(name, value);
        return this;
    }

    public DtoField dtoFieldFor(final String name) {
        if (!this.valueIndex.keySet().contains(name)) throw new IllegalStateException(String.format(
                "The field named: %s is not part of the DtoObject: %s",
                name,
                this.getClass().getName()));

        return this.valueIndex.get(name);
    }

    public boolean isValueInitialised(final String name) {
        return this.dtoFieldFor(name).isInitialized();
    }

    public Map<String,Object> currentValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoField> entry : this.valueIndex.entrySet()) {
            if (entry.getValue().isInitialized()) map.put(
                    entry.getKey(),
                    entry.getValue().currentValue());
        }

        return map;
    }

    public Map<String,Object> originalValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoField> entry : this.valueIndex.entrySet()) {
            map.put(
                    entry.getKey(),
                    entry.getValue().originalValue());
        }

        return map;
    }

    public Map<String,DtoField> dirtyFields() {

        final Map<String,DtoField> map = new HashMap<>();

        for (final Entry<String,DtoField> entry : this.valueIndex.entrySet()) {
            if (entry.getValue().isDirty()) map.put(
                    entry.getKey(),
                    entry.getValue());
        }

        return map;
    }

    public Map<String,Object> dirtyValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoField> entry : this.valueIndex.entrySet()) {
            if (entry.getValue().isDirty()) map.put(
                    entry.getKey(),
                    entry.getValue().currentValue());
        }

        return map;
    }

    public Map<String,DtoField> staleFields() {

        final Map<String,DtoField> map = new HashMap<>();

        for (final Entry<String,DtoField> entry : this.valueIndex.entrySet()) {
            if (entry.getValue().isStale()) map.put(
                    entry.getKey(),
                    entry.getValue());
        }

        return map;
    }

    public Map<String,Object> staleValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoField> entry : this.valueIndex.entrySet()) {
            if (entry.getValue().isStale()) map.put(
                    entry.getKey(),
                    entry.getValue().currentValue());
        }

        return map;
    }

    public Map<String,DtoField> conflictedFields() {

        final Map<String,DtoField> map = new HashMap<>();

        for (final Entry<String,DtoField> entry : this.valueIndex.entrySet()) {
            if (entry.getValue().isConflicted()) map.put(
                    entry.getKey(),
                    entry.getValue());
        }

        return map;
    }

    public Map<String,Object> conflictedValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoField> entry : this.valueIndex.entrySet()) {
            if (entry.getValue().isConflicted()) map.put(
                    entry.getKey(),
                    entry.getValue().currentValue());
        }

        return map;
    }

    public void onRefresh(final DtoRefreshEvent dtoRefreshEvent) {
        // Guaranteed to be for this instance. One eventbus per *identity*.
        if (this == dtoRefreshEvent.source()) return;

        // should never happen
        if (this.isNew()) throw new IllegalStateException("Cannot refresh a new instance");

        this.resetValues(dtoRefreshEvent.source());
    }

    public void register(final DtoBus bus) {

        // cannot assign to a null buss
        Guard.notNull(bus);

        // only allow one bus at a time
        if ((this.bus != null) && (this.bus != bus))
            throw new IllegalStateException(String.format(
                    "Already register to bus: %s, cannot also register with bus: %s.",
                    this.bus,
                    bus));

        // set the bus
        this.bus = bus;

        // only register it if there is an identity (remember we may be switching bus)
        if (this.identity != null) this.bus.register(this);

    }

    public void unregister(final DtoBus bus) {

        // check we are unregistering from correct bus
        if (this.bus != bus)
            throw new IllegalStateException(String.format(
                    "Cannot unregister to bus: %s when registered to bus: %s.",
                    bus,
                    this.bus));

        // deregister only if we got an identity
        if (this.identity != null) this.bus.unregister(this);

        // unassign the bus
        this.bus = null;

    }

    @Override
    public boolean isDirty() {
        for (final ChangeTracked field : this.cycleIndex.values()) {
            if (field.isDirty()) return true;
        }
        return false;
    }

    @Override
    public boolean isStale() {
        for (final ChangeTracked field : this.cycleIndex.values()) {
            if (field.isStale()) return true;
        }
        return false;
    }

    @Override
    public boolean isConflicted() {
        for (final ChangeTracked field : this.cycleIndex.values()) {
            if (field.isConflicted()) return true;
        }
        return false;
    }

    @Override
    public void acceptChanges() {

        // pass accept down tree
        for (final ChangeTracked field : this.cycleIndex.values()) {
            field.acceptChanges();
        }

        // if we were new, we have to do stuff
        if (this.isNew()) {

            // create the new identity (will no longer be 'new')
            this.identity = this.toDtoIdentity();

            // finish registering now we have identity info
            if (this.bus != null) this.bus.register(this);

        }

        // we could fire these on reset also, but i'm going to say that the reset
        // is too low level and we'll fire them on the reset/cancel combination.
        // This is less likely to get confusing, at the small cost of possibly doing
        // an additional resets if objects are not correctly cancelled when disposed.
        if (this.bus != null) this.bus.post(new DtoRefreshEvent(this));

    }

    private DtoIdentity toDtoIdentity() {

        final Map<String,Object> identityValues = new HashMap<>();

        for (final Map.Entry<String,DtoField> entry : this.identityIndex.entrySet()) {

            final Object value = entry.getValue().currentValue();

            if (value == null) throw new UncheckedException(String.format(
                    "The identity field: %s is null",
                    entry.getKey()));

            identityValues.put(
                    entry.getKey(),
                    value);
        }

        return new DtoIdentity(this.identityName, identityValues);

    }

    public Dto acceptThis() {
        this.acceptChanges();
        return this;
    }

    @Override
    public void cancelChanges() {

        for (final ChangeTracked field : this.cycleIndex.values()) {
            field.cancelChanges();
        }

        if (this.bus != null) this.bus.post(new DtoRefreshEvent(this));

    }

    public Dto cancelThis() {
        this.cancelChanges();
        return this;
    }

    public MetadataContainer metadata() {
        return this.metadata;
    }

    public MetadataContainer metadataFor(final String name) {
        return this.dtoFieldFor(name).metadata();
    }

    @Override
    public final int hashCode() {
        // don't override
        return super.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        // don't override
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("fields", this.valueIndex.values())
                          .toString();
    }

}
