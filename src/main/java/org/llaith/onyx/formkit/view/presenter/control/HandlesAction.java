package org.llaith.onyx.formkit.view.presenter.control;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 20:53
 */
public interface HandlesAction {

    public interface ActionHandler {
        void handleAction();
    }

    void setActionHandler(ActionHandler handler);

}
