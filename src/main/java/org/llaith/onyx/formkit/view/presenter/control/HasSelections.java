package org.llaith.onyx.formkit.view.presenter.control;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 21:38
 *
 * For listboxes, comboboxes, listbuilders, optiongroups, cbgroups.
 *
 */
public interface HasSelections<S> {

    void setSelections(List<S> selections);

}
