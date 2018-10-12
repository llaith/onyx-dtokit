package org.llaith.onyx.formkit.view.form.editor;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 29-Aug-2009
 * Time: 19:49:30
 */
public class BooleanPropertyEditor implements PropertyEditor<Boolean> {

    @Override
    @SuppressWarnings("boxing")
    public Boolean fromString(final String value) {
        return Boolean.parseBoolean(value);
    }

    @Override
    @SuppressWarnings("boxing")
    public String toString(final Boolean value) {
        return Boolean.toString(value);
    }
}
