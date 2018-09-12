package org.llaith.onyx.formkit.view.presenter.display;

import org.llaith.onyx.formkit.view.DisplayableCloseVetoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 12/01/2012
 * Time: 06:55
 *
 * works for the case of portals (all the displays will be added at the start, or windows
 * where a display will be added to the root compound display when needed).
 *
 * Note, for app windows to work, the application root display should be a compound display
 * with one other compound display for the root layout, and the true root used to add
 * app-windows.
 *
 */
public class CompoundScreen implements Screen {

    private final List<Screen> screens = new ArrayList<Screen>();

    public CompoundScreen(final List<Screen> screens) {
        this.screens.addAll(screens);
    }


    @Override
    public void open() {
        for (final Screen screen : this.screens) {
            screen.open();
        }
    }

    @Override
    public void willClose() throws DisplayableCloseVetoException {
        for (final Screen screen : this.screens) {
            screen.willClose();
        }
    }

    @Override
    public void close() {
        for (final Screen screen : this.screens) {
            screen.close();
        }
    }

    @Override
    public void show() {
        for (final Screen screen : this.screens) {
            screen.show();
        }
    }

    @Override
    public void hide() {
        for (final Screen screen : this.screens) {
            screen.hide();
        }
    }

}
