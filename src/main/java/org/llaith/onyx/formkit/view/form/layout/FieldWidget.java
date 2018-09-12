package org.llaith.onyx.formkit.view.form.layout;


import org.llaith.onyx.toolkit.lang.Guard;

import javax.annotation.Nullable;
import java.io.Serializable;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 17, 2008
 * Time: 3:07:58 PM
 * <p/>
 * Notes: it's VERY hard to decide whether to lock the controls to a type of
 * field. The current answer is Yes. Here's why.
 * <p/>
 * 1) We can often have multiple controls types per field.
 * Eg. Checkbox or RadioButton's for BooleanField and TextControl,
 * TextAreaControl, ComboBox, DropdownBox for StringFields.
 * 2) Most Controls don't make sense for multi types of Fields, eg: DateControl,
 * CheckBox,
 * <p/>
 * Complications:
 * 1) Some 'controls' have to field. Labels may but StaticText does not. Also
 * Links. Hence we need a heirarchy.
 * 2) Some controls like TextBox are multi-type. We loose convienience there.
 */
public abstract class FieldWidget implements Serializable {

    @Nullable
    private final String fieldLabel;
    private final int order;

    @SuppressWarnings("boxing")
    protected FieldWidget(final String fieldLabel, final int order) {
        this.fieldLabel = Guard.notNull(fieldLabel);
        this.order = Guard.notNull(order);
    }

    @Nullable
    public String fieldLabel() {
        return this.fieldLabel;
    }

    public int order() {
        return this.order;
    }

}
