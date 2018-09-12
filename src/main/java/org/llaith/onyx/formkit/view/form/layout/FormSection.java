package org.llaith.onyx.formkit.view.form.layout;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 17, 2008
 * Time: 10:30:34 AM
 */
public class FormSection implements Serializable {


    private final int index;
    private final String title;
    private final String description;
    private final List<FieldSection> fieldSections = new ArrayList<FieldSection>();

    public FormSection(final int index, final String title, final String description, final List<FieldSection> fieldSections) {
        this.index = index;
        this.title = title;
        this.description = description;
        this.fieldSections.addAll(fieldSections);
    }

    public int index() {
        return this.index;
    }

    public String title() {
        return this.title;
    }

    public String description() {
        return this.description;
    }


    public List<FieldSection> fieldSections() {
        return this.fieldSections;
    }

}
