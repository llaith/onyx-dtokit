package org.llaith.onyx.formkit.view.presenter.control;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 20:53
 *
 * Handles any 'action' which takes an associated object. This can be anything from a embedded button in a
 * table row, to a drag-and-drop handler in a tree. Also, a right-click-menu action in a tree would also
 * use one of these.
 *
 * Note a search button on a form can be a action button
 * because we know the data for the action (it's hardcoded), but table results don't know the data in
 * advance as it's queried.
 *
 */
public interface HandlesSelection<T> {

    public interface SelectionHandler<T> {
        void handleSelection(T selected);
    }

    void setSelectionHandler(SelectionHandler<T> handler);

}
