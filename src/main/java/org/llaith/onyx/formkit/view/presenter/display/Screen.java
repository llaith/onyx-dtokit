package org.llaith.onyx.formkit.view.presenter.display;

import org.llaith.onyx.formkit.view.Displayable;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 12/01/2012
 * Time: 06:56
 *
 * DESIGN:
 *
 *      1) Displays are the screen slots. You could either subclass the displays
 *      to make the specific screens directly, or use the presenter model to make
 *      a more formal/testable version.
 *
 *      2) This needs to accomodate GUIs like portals, RIAs, and traditional+popups.
 *      For this reason is is assumed that a display 'on top' of another display
 *      (via the stack) is occluding it.
 *
 *      3) Breadcrumbs work with displays not presentables.
 *
 *      4) The intention is that the presenters allow you to generalize gui patterns,
 *      and the displays are the holders for those patterns. The line is a bit blurry.
 *
 *
 */
public interface Screen extends Displayable {

    // i think there will be more in here later

}
