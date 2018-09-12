package org.llaith.onyx.formkit.view.form.layout;


import org.llaith.onyx.formkit.dto.validation.Property;
import com.llaith.util.lang.Guard;

import javax.annotation.Nullable;


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
public class PropertyWidget<T> extends FieldWidget {

    public static abstract class FieldAdapter<T> {

        public final Property<T> property;

        public FieldAdapter(final Property<T> property) {
            this.property = property;
        }

    }

    public static enum Access {
        READ_WRITE, READ_ONLY
    }

    @Nullable
    private final FieldAdapter<T> fieldAdapter;
    @Nullable
    private final Access access;
    private final FieldDependency dependency;

    public PropertyWidget(final String fieldLabel, final int order, final FieldAdapter<T> fieldAdapter, final Access access) {
        this(fieldLabel, order, fieldAdapter, access, null);
    }

    public PropertyWidget(final String fieldLabel, final int order, final FieldAdapter<T> fieldAdapter, final Access access, final FieldDependency dependency) {
        super(fieldLabel, order);
        this.fieldAdapter = Guard.expectParam("fieldAdapter",fieldAdapter);
        this.access = Guard.expectParam("access",access);
        this.dependency = dependency;
    }

    public Property<T> property() {
        return this.fieldAdapter.property;
    }

    @Nullable
    public Access access() {
        return this.access;
    }

    public FieldDependency dependecy() {
        return this.dependency;
    }
}
