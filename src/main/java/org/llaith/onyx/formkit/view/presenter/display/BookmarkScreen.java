package org.llaith.onyx.formkit.view.presenter.display;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 21:45
 *
 * A display that can be bookmarked needs to also implement this interface.
 * These are basically entry-points. A bookmarker widget should hold references
 * to these.
 *
 */
public interface BookmarkScreen extends Screen {

    // actually don't know what this should be - it's really a marker interface.

    String getBookmark();

}
