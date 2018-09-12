package org.llaith.onyx.formkit.dto.validation;


import com.llaith.dto.Dto;


public interface PropertySetValidator<T extends Dto> {

    void validate(T value) throws PropertySetValidationException;

}
