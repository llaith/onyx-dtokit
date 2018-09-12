package org.llaith.onyx.formkit.view.form.editor;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 29-Aug-2009
 * Time: 19:40:57
 */
public interface PropertyEditor<T extends Serializable> {

    @Nullable
    T fromString(String value);
    String toString(T value);

}
