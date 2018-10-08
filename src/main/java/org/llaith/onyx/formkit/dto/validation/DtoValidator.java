package org.llaith.onyx.formkit.dto.validation;


import org.llaith.onyx.formkit.dto.Dto;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @param <T> Note: this doesn't use PropertySetValidators as that is a form concept.
 * @author llaith
 */
public class DtoValidator<T extends Dto> {


    private final Map<String,Property<?>> properties;

    public DtoValidator() {
        this(new ArrayList<Property<?>>());
    }

    public DtoValidator(final Collection<Property<?>> properties) {
        this.properties = indexProperties(properties);
    }


    private Map<String,Property<?>> indexProperties(final Collection<Property<?>> properties) {

        final Map<String,Property<?>> index = new HashMap<>();

        for (final Property<?> f : properties) index.put(f.id().name(), f);

        return index;
    }

    public void addProperties(final Property<?>... properties) {
        this.addProperties(Arrays.asList(properties));
    }

    public void addProperties(final List<Property<?>> properties) {
        this.properties.putAll(indexProperties(properties));
    }

    public Property<?> property(final String propertyName) {
        return this.properties.get(propertyName);
    }

    public Property<?> property(final Id id) {
        return this.property(id.name());
    }


    @SuppressWarnings("unchecked")
    public <X> Property<X> property(final TypedId<X> id) {
        return (Property<X>)this.property(id.name());
    }


    @SuppressWarnings("unchecked")
    public <X> X propertyValue(final T dto, final TypedId<X> id) {
        return (X)dto.get(id.name());
    }


    public List<Property<?>> properties() {
        return new ArrayList<Property<?>>(this.properties.values());
    }


    public List<String> validateUndefineds(final Id[] propertyIds) {
        final List<String> undefineds = new ArrayList<String>();
        for (final Id id : propertyIds) {
            if (!this.properties.containsKey(id.name())) undefineds.add(id.name());
        }
        return undefineds;
    }


    public Map<String,String> validateRequired(final T dto) {
        final Map<String,String> fails = new HashMap<>();
        for (final Property<?> p : this.properties.values()) {
            final String fail = failsRequired(dto, p);
            if (fail != null) fails.put(p.id().name(), fail);
        }
        return fails;
    }

    @Nullable
    private String failsRequired(final T dto, final Property<?> property) {
        if ((dto.get(property.id().name()) == null) && property.required())
            return property.id().name() + " is required";
        return null;
    }


    public Map<String,String> validateErrors(final T dto) {
        final Map<String,String> fails = new HashMap<>();
        for (final Property<?> f : this.properties.values()) {
            try {
                final Object val = dto.get(f.id().name());
                if (val != null) f.validate(val);
            } catch (final ClassCastException | PropertyValidationException e) {
                fails.put(f.id().name(), e.getMessage());
            }
        }
        return fails;
    }

}
