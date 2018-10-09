package org.llaith.onyx.formkit.view.presenter.control;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 20:53
 *
 * Some systems have a checkbox button that works as a button not an input and gives a T/F value.
 *
 */
public interface HandlesCheckAction {

    public interface CheckActionHandler {
        void handleAction(boolean checked);
    }


    void setCheckActionHandler(CheckActionHandler handler);

}
