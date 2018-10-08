package org.llaith.onyx.formkit.view.form.layout;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 17, 2008
 * Time: 3:12:07 PM
 */
public class FormLayout implements Serializable {


    private final List<FormSection> formSections = new ArrayList<>();

    public FormLayout(final List<FormSection> formSections) {
        this.formSections.addAll(formSections);
    }


    public List<FormSection> formSections() {
        return new ArrayList<>(this.formSections);
    }

}
