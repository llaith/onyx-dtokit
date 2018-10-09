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
public class IntegerPropertyEditor implements PropertyEditor<Long> {

    private final Logger log = LoggerFactory.getLogger(IntegerPropertyEditor.class);

    private final NumberFormat format;

    public IntegerPropertyEditor(final NumberFormat format) {
        this.format = format;
    }

    @Nullable
    @SuppressWarnings("boxing")
    @Override
    public Long fromString(final String value) {
        try {
            return this.format.parse(value).longValue();
        }
        catch ( final ParseException e) {
            this.log.warn("Cannot convert value");
            return null;
        }
    }

    @Override
    public String toString(final Long value) {
        return this.format.format(value);
    }

}
