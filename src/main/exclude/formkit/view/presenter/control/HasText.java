package org.llaith.onyx.formkit.view.presenter.control;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 14/01/2012
 * Time: 07:24
 *
 * We would have this interface not for normal static text, but for when text comes from the db - like in a
 * CMS or portal interface. Remember, anything that comes from the Model has a view adapter representation.
 * If it's static text and we want to abstract that - we do that through a smart builder.
 *
 */
public interface HasText {

    void setText(String text);

}
