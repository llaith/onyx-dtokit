package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import org.llaith.onyx.formkit.dto.validation.Property;
import com.llaith.util.lang.Guard;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;

import javax.annotation.Nullable;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Aug 3, 2008
 * Time: 3:50:56 AM
 * <p/>
 * This is a control which lays out the ture/false options with labels. Usually
 * a radio group.
 * Interestingly, it cannot be done with the RadioGroup<M> approach because of
 * the labels
 * <p/>
 * TODO: internationalize all these - call to the formController to get the
 * localized versions
 */
public class SwitchWidget extends PropertyWidget<Boolean> {


    public static class SwitchFieldAdapter extends FieldAdapter<Boolean> {

        public SwitchFieldAdapter(final Property<Boolean> property) {
            super(property);
        }

    }

    @Nullable
    private final String trueLabel;
    @Nullable
    private final String falseLabel;

    public SwitchWidget(final String fieldLabel, final int order, final SwitchFieldAdapter fieldAdapter, final Access access) {
        this(fieldLabel, order, fieldAdapter, access, "yes", "no");
    }

    public SwitchWidget(final String fieldLabel, final int order, final SwitchFieldAdapter fieldAdapter, final Access access, final String trueLabel, final String falseLabel) {
        super(fieldLabel, order, fieldAdapter, access);
        this.trueLabel = Guard.expectParam("trueLabel",trueLabel);
        this.falseLabel = Guard.expectParam("falseLabel",falseLabel);
    }

    @Nullable
    public String trueLabel() {
        return this.trueLabel;
    }

    @Nullable
    public String falseLabel() {
        return this.falseLabel;
    }

}
