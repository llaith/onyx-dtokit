package org.llaith.onyx.formkit.view.form.editor;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 29-Aug-2009
 * Time: 19:51:43
 */
public class StringPropertyEditor implements PropertyEditor<String> {

    public String fromString(final String value) {
        return value;
    }

    public String toString(final String value) {
        return value;
    }

}
