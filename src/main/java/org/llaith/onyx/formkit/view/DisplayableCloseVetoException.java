package org.llaith.onyx.formkit.view;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 16-Dec-2009
 * Time: 21:22:44
 *
 * The controller that aborts gets a 'display'' command
 */
public class DisplayableCloseVetoException extends Exception {

    public DisplayableCloseVetoException() {
        super("Presenter close vetoed.");
    }
}
