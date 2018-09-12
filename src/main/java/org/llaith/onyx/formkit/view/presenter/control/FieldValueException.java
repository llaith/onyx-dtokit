package org.llaith.onyx.formkit.view.presenter.control;


/**
 * Created by IntelliJ IDEA.
 * User: ndoughty
 * Date: Jun 18, 2009
 * Time: 9:48:32 AM
 *
 * For things like conversion errors
 *
 *
 */
public class FieldValueException extends Exception {

    public FieldValueException(final String s) {
        super(s);
    }

    public FieldValueException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    public FieldValueException(final Throwable throwable) {
        super(throwable);
    }
}
