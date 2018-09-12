package org.llaith.onyx.formkit.view.presenter.display;

import org.llaith.onyx.formkit.view.DisplayableCloseVetoException;
import org.llaith.onyx.formkit.view.presenter.Presenter;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 12/01/2012
 * Time: 07:08
 */
public class PresenterScreen implements Screen {

    private final Presenter<?,?> presenter;

    public PresenterScreen(final Presenter<?, ?> presenter) {
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

}
