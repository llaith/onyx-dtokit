package org.llaith.onyx.formkit.dto.validation;


import javax.annotation.Nullable;


public class Property<T> {

    private final Class<T> type;
    private final TypedId<T> id;
    private final boolean required;
    private final T defValue;
    private final PropertyValidator<T> validator;

    private Property(final Class<T> type, final TypedId<T> id, final boolean required, final T defValue) {
        this(type,id,required,defValue,null);
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
        this(typeName,id,required,defValue,null);
    }

    @SuppressWarnings("unchecked")
    private Property(final String typeName, final TypedId<T> id, final boolean required, final T defValue, final PropertyValidator<T> validator) {
        try {
            this.type = (Class<T>)Class.forName(Guard.expectParam("typeName",typeName));
        }
        catch ( final ClassNotFoundException e) {
            throw new WrappedException(e);
        }
        this.id = id;
        this.required = required;
        this.defValue = defValue;
        this.validator = validator;
    }

    public Class<T> type() {
        return this.type;
    }

    public boolean isType( final Class<?> clazz) {
        return clazz.isAssignableFrom(this.type);
    }

    @SuppressWarnings("unchecked")
    public void validate(@Nullable final Object o) throws ClassCastException, PropertyValidationException {
        if (o == null) return;
        if (!isType(o.getClass())) throw new ClassCastException(String.format("Expected type of: %s but received a type of: %s ",this.type,o.getClass()));
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.defValue == null) ? 0 : this.defValue.hashCode());
        result = prime * result + ((this.id == null) ? 0 : this.id.name().hashCode());
        result = prime * result + (this.required ? 1231 : 1237);
        result = prime * result + ((this.type == null) ? 0 : this.type.getName().hashCode());
        return result;
    }

    @Override
    public boolean equals( final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Property<?> other = (Property<?>)obj;
        if (this.defValue == null) {
            if (other.defValue != null) return false;
        } else if (!this.defValue.equals(other.defValue)) return false;
        if (this.id == null) {
            if (other.id != null) return false;
        } else if (!this.id.name().equals(other.id.name())) return false;
        if (this.required != other.required) return false;
        if (this.type == null) {
            if (other.type != null) return false;
        } else if (!this.type.getName().equals(other.type.getName())) return false;
        return true;
    }

}
