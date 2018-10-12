package org.llaith.onyx.formkit.view.presenter.control;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 20:53
 */
public interface HandlesMultipleSelection<T> {

    public interface MultipleSelectionHandler<T> {
        void handleMulitpleSelection(List<T> selected);
    }

    void setMultipleSelectionHandler(MultipleSelectionHandler<T> handler);

}
