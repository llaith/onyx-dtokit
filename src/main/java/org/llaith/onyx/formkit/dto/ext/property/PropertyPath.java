package org.llaith.onyx.formkit.dto.ext.property;


import org.llaith.onyx.formkit.dto.Dto;

/**
 * @author llaith
 */
public class PropertyPath {

    private final String path;
    private final String[] branches;
    private final String leaf;

    private Dto dto;

    public PropertyPath(final String path) {

        this.path = path;

        this.leaf = leafOf(path);

        this.branches = elementsOf(branches(path));

    }

    public void attach(final Dto dto) {

        this.dto = dto;

    }

    private String leafOf(final String path) {

        return path.substring(path.lastIndexOf('.'));

    }

    private String[] elementsOf(final String searchString) {

        return searchString.split("\\.");

    }

    private String branches(final String path) {

        return path.substring(0, path.lastIndexOf('.'));

    }

    public String path() {

        return this.path;

    }

    public void set(final Object value) {

        this.from(this.dto).set(this.leaf, value);

    }

    public Object get() {

        return this.from(this.dto).get(this.leaf);

    }

    @SuppressWarnings("squid:S2692")
    private Dto from(final Dto dto) {

        Dto search = dto;

        if (this.path.indexOf('.') > 0) { // ignore first element

            for (final String s : this.branches) {

                search = (Dto)dto.get(s);

            }

        }

        return search;

    }

}
