package org.llaith.onyx.formkit.view;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 12/01/2012
 * Time: 07:10
 *
 * This is the component that could be some sort of events?
 */
public interface Displayable {

    void open(); // is an attach/open

    void willClose() throws DisplayableCloseVetoException; // use eventbus?

    void close(); // a hide() is sent before this

    void show(); // such as navigation forward, minimize, turn off expensive gui widgets.

    void hide(); // resume active components.

}
