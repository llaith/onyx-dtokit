package org.llaith.onyx.formkit.dto.ext.property;


import org.llaith.onyx.formkit.dto.validation.PropertyValidationException;
import org.llaith.onyx.formkit.dto.validation.PropertyValidator;
import org.llaith.onyx.toolkit.lang.Guard;

import javax.annotation.Nullable;

import static org.llaith.onyx.toolkit.fn.ExcecutionUtil.rethrowOrReturn;


public class Property<T> {

    private final Class<T> type;
    private final TypedId<T> id;
    private final boolean required;
    private final T defValue;
    private final PropertyValidator<T> validator;

    private Property(final Class<T> type, final TypedId<T> id, final boolean required, final T defValue) {
        this(type, id, required, defValue, null);
    }

    private Property(final Class<T> type, final TypedId<T> id, final boolean required, final T defValue, final PropertyValidator<T> validator) {
        super();
        this.type = type;
        this.id = id;
        this.required = required;
        this.defValue = defValue;
        this.validator = validator;
    }

    private Property(final String typeName, final TypedId<T> id, final boolean required, final T defValue) {
        this(typeName, id, required, defValue, null);
    }

    @SuppressWarnings("unchecked")
    private Property(final String typeName, final TypedId<T> id, final boolean required, final T defValue, final PropertyValidator<T> validator) {

        this.type = rethrowOrReturn(() -> (Class<T>)Class.forName(Guard.notNull(typeName)));

        this.id = id;
        this.required = required;
        this.defValue = defValue;
        this.validator = validator;

    }

    public Class<T> type() {
        return this.type;
    }

    public boolean isType(final Class<?> clazz) {
        return clazz.isAssignableFrom(this.type);
    }

    @SuppressWarnings("unchecked")
    public void validate(@Nullable final Object o) throws ClassCastException, PropertyValidationException {
        if (o == null) return;
        if (!isType(o.getClass()))
            throw new ClassCastException(String.format("Expected type of: %s but received a type of: %s ", this.type, o.getClass()));
        if (this.validator != null) {
            this.validator.validate((T)o);
        }
    }

    public T cast(final Object o) {
        return this.type.cast(o);
    }

    public TypedId<T> id() {
        return this.id;
    }

    public boolean required() {
        return this.required;
    }

    public T defValue() {
        return this.defValue;
    }

}
