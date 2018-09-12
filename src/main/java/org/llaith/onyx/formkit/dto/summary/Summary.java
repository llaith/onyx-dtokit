package org.llaith.onyx.formkit.dto.summary;


import org.llaith.onyx.formkit.dto.Dto;
import org.llaith.onyx.formkit.dto.session.DtoRefreshEvent;
import org.llaith.onyx.formkit.dto.session.DtoRefreshListener;
import org.llaith.onyx.formkit.dto.session.DtoSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Summary implements DtoRefreshListener {

    private final Map<String,PropertyPath<?>> properties;

    public Summary(final List<PropertyPath<?>> properties) {
        this.properties = this.indexProperties(properties);
    }

    private Map<String,PropertyPath<?>> indexProperties(final List<PropertyPath<?>> properties) {
        final Map<String,PropertyPath<?>> index = new HashMap<>();
        for (final PropertyPath<?> id : properties) {
            index.put(id.path(), id);
        }
        return index;
    }

    public void attachTo(final DtoSession dto) {
        this.dto = dto;
        for (final PropertyPath<?> id : this.properties.values()) {
            id.attach(dto);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onRefresh(final DtoRefreshEvent event) {

        final Dto source = event.source();

        if ((this.dto != source) && (this.dto.equals(source))) {
            this.attachTo((T)source);
        }

    }
}
