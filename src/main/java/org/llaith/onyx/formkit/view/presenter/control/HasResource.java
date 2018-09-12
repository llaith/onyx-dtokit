package org.llaith.onyx.formkit.view.presenter.control;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 14/01/2012
 * Time: 07:28
 */
public interface HasResource<T extends ResourceType> {

    void setResource(Resource<T> resource);

}
