package org.llaith.onyx.formkit.view.presenter.display;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 21:45
 *
 * A display that can be breadcrumbed needs to also implement this interface.
 * These are the next/prev steps. The Breadcrumb widget should hold references
 * to these.
 *
 */
public interface BreadcrumbScreen extends Screen {


    String getBreadcrumb();

}
