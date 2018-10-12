package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import org.llaith.onyx.formkit.dto.validation.Property;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;

import javax.annotation.Nullable;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 17, 2008
 * Time: 4:29:11 PM
 * <p/>
 * DESIGN: The only practical design is for the controls to completely subsume
 * the Fields. So we never pass the field
 * tot he control. We need to delegate validators and other constrainsts to the
 * field from the control. This allows us to
 * set up automatic/extra/or stricter checked constraints like DateRange
 * checking that the 'min' of the to date is
 * after the min of the from date.
 * <p/>
 * WARNING. This is one of the controls that answer the function call hasValue()
 * (used by required sets) with their currently selected state!
 * This means that you must make true the state that enables the set (in other
 * words it defaults to false). Otherwisze you will need'
 * to use enableds like Person/Part or use a RadioButtonGroup or dropdown setup
 * with None/No/Yes
 * <p/>
 * Due to the fact that we want these to be DYNAMIC, we have to pass
 * FieldType<?> not a subclass (would have a dependency on the
 * profile) and we can't use FieldControl<T extends xxx> because that would only
 * allow one control type per field type which is not
 * how i want to use them. A simple number field should be TextBoxControl <-
 * IntegerField. The factories do the 'instanceof's' to get
 * the right types.
 * <p/>
 * In my case the FieldAdapter approach works because there are multiple input
 * types, but this class doesn't care about the actual type.
 * A factory will be building it and as they'll be in collections anyway -
 * they'll already be instanceof checks.
 */
public class CheckBoxWidget extends PropertyWidget<Boolean> {


    public static class CheckBoxFieldAdapter extends FieldAdapter<Boolean> {

        public CheckBoxFieldAdapter(final Property<Boolean> property) {
            super(property);
        }
    }

    private final String boxLabel;

    public CheckBoxWidget(final String fieldLabel, final int order, final CheckBoxFieldAdapter fieldAdapter, final Access access, final @Nullable String boxLabel) {
        super(fieldLabel, order, fieldAdapter, access);

        this.boxLabel = boxLabel;
    }

    public String boxLabel() {
        return this.boxLabel;
    }

}
