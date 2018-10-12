package org.llaith.onyx.formkit.view.presenter.control;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 21:16
 *
 * Handles things like the ColumnBrowser and the linked combos.
 *
 */
public interface HandlesSteppedResults<T> {

    public interface SteppedResultsHandler<T> {

        List<T> handleSteppedResults(List<T> path);
    }

    void setSteppedResultsHandler(SteppedResultsHandler<T> handler);

}
