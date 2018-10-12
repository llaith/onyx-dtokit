package org.llaith.onyx.formkit.view.form.editor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.text.ParseException;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 30-Aug-2009
 * Time: 12:08:45
 */
public class DecimalPropertyEditor implements PropertyEditor<Double> {

    private final Logger log = LoggerFactory.getLogger(DecimalPropertyEditor.class);

    private final NumberFormat format;

    public DecimalPropertyEditor(final NumberFormat format) {
        this.format = format;
    }

    @Nullable
    @SuppressWarnings("boxing")
    @Override
    public Double fromString(final String value) {
        try {
            return this.format.parse(value).doubleValue();
        }
        catch ( final ParseException e) {
            this.log.warn("Cannot convert value");
            return null;
        }
    }

    @Override
    public String toString(final Double value) {
        return this.format.format(value);
    }

}
