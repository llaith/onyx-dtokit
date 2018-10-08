package org.llaith.onyx.formkit.dto.validation;


import org.llaith.onyx.formkit.dto.Dto;

public interface PropertySetValidator<T extends Dto> {

    void validate(T value) throws PropertySetValidationException;

}
