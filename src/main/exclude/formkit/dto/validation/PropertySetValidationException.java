package org.llaith.onyx.formkit.dto.validation;


import java.util.Arrays;

public class PropertySetValidationException extends Exception {



    private final String[] fields;
    private final String error;

    public PropertySetValidationException(final String error, final String... fields) {
        super(String.format(
                "A validation error has accoured: %s for the set of fields: %s. ",
                error,
                Arrays.toString(fields)));
        this.fields = fields;
        this.error = error;
    }

    public String[] fields() {
        return this.fields;
    }

    public String error() {
        return this.error;
    }

}
