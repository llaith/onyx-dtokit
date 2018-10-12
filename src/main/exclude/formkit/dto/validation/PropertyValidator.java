package org.llaith.onyx.formkit.dto.validation;


public interface PropertyValidator<T> {

    void validate(T value);

}
