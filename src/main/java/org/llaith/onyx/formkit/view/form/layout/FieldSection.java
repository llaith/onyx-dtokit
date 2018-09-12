package org.llaith.onyx.formkit.view.form.layout;


import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 16, 2008
 * Time: 4:48:06 PM
 */
public class FieldSection implements Serializable {

    private final int index;
    private final String title;
    private final String description;
    private final
    @Nullable
    String mounting; // e.g. "${1}" or
    // "wait {num_days} day(s)\n ---or---\n wait {num_hours} hour(s)"
    // or
    // "<p>wait {num_days} day(s)</p><p align="c">---or---</p><p>wait {num_hours} hour(s)</p>"
    private final List<FieldWidget> fieldWidgets = new ArrayList<FieldWidget>();

    public FieldSection(final int index, final String title, final String description, final String mounting, final List<FieldWidget> fieldWidgets) {
        this.index = index;
        this.title = title;
        this.description = description;
        this.mounting = mounting;
        this.fieldWidgets.addAll(fieldWidgets);
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

    public String getMounting() {
        return this.mounting;
    }


    public List<FieldWidget> fieldControls() {
        return new ArrayList<FieldWidget>(this.fieldWidgets);
    }

}
