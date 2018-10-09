package org.llaith.onyx.formkit.view.presenter;

import org.llaith.onyx.formkit.controller.Controller;
import org.llaith.onyx.formkit.controller.ControllerDisposeAbortException;
import org.llaith.onyx.formkit.view.DisplayableCloseVetoException;
import org.llaith.onyx.formkit.view.presenter.Presenter;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 12/01/2012
 * Time: 07:08
 */
public class PresenterController implements Controller {

    private final Presenter<?,?> presenter;

    public PresenterController(final Presenter<?,?> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void open() {
        this.presenter.open();
    }

    @Override
    public void willClose() throws DisplayableCloseVetoException {
        this.presenter.willClose();
    }

    @Override
    public void close() {
        this.presenter.close();
    }

    @Override
    public void show() {
        this.presenter.show();
    }

    @Override
    public void hide() {
        this.presenter.hide();
    }

    @Override
    public Controller activate() {
        return null;
    }

    @Override
    public Controller deactivate() {
        return null;
    }

    @Override
    public Controller willDispose() throws ControllerDisposeAbortException {
        return null;
    }

    @Override
    public Controller dispose() {
        return null;
    }
}
