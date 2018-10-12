package org.llaith.onyx.formkit.view.form.control;


import org.llaith.onyx.formkit.view.presenter.control.TakesValue;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Aug 18, 2008
 * Time: 2:35:27 PM
 */
public interface FieldReadOnlyAdapter<T> extends TakesValue<T> {

    public void clearValue();

}
