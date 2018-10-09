package org.llaith.onyx.formkit.dto.ext.property;

import com.google.common.reflect.TypeToken;
import org.llaith.onyx.toolkit.reflection.IdToken;

/**
 *
 */
public class TypedId<T> extends IdToken<String,T> {

    public TypedId(final String id, final Class<T> target) {
        super(id, target);
    }

    public TypedId(final String id, final TypeToken<T> target) {
        super(id, target);
    }

}
