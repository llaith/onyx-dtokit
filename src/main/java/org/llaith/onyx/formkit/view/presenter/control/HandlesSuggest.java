package org.llaith.onyx.formkit.view.presenter.control;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 21:16
 *
 * Handles things like the suggestion box.
 *
 * Question: ValueChangeHandler vs selectionHandler?
 *
 */
public interface HandlesSuggest<T> {

    public interface SuggestHandler<T> {

        List<T> handleSuggest(T value);
    }

    void setSuggestHandler(SuggestHandler<T> handler);

}
