package org.llaith.onyx.formkit.view.form.editor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 29-Aug-2009
 * Time: 19:53:14
 */
public class DateTimePropertyEditor implements PropertyEditor<Date> {

    private final Logger log = LoggerFactory.getLogger(DateTimePropertyEditor.class);

    private final DateFormat formatter;

    public DateTimePropertyEditor(final DateFormat formatter) {
        this.formatter = formatter;
    }

    @Nullable
    @Override
    public Date fromString(final String value) {
        try {
            return this.formatter.parse(value);
        }
        catch ( final ParseException e) {
            this.log.warn("Cannot convert value");
            return null;
        }
    }

    @Override
    public String toString(final Date value) {
        return this.formatter.format(value);
    }

}
