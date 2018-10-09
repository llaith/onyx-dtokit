package org.llaith.onyx.formkit.dto.validation;


public class PropertyValidationException extends Exception {



    public PropertyValidationException(final String message, final Throwable cause) {
        super(message,cause);
    }

    public PropertyValidationException(final String message) {
        super(message);
    }

}
