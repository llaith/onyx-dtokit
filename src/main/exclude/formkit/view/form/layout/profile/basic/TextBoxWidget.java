package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import org.llaith.onyx.formkit.dto.validation.Property;
import com.llaith.util.lang.Guard;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;

import javax.annotation.Nullable;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 17, 2008
 * Time: 4:43:11 PM
 */
public class TextBoxWidget extends PropertyWidget<String> {


    public static class TextBoxFieldAdapter extends FieldAdapter<String> {

        public TextBoxFieldAdapter(final Property<String> property) {
            super(property);
        } // Note would have to extend Selections if add neo types
    }

    @Nullable
    private final String hint;
    private final int width;

    @SuppressWarnings("boxing")
    public TextBoxWidget(final String fieldLabel, final int order, final TextBoxFieldAdapter fieldAdapter, final Access access, final String hint, final int width) {
        super(fieldLabel, order, fieldAdapter, access);
        this.hint = Guard.expectParam("hint",hint);
        this.width = Guard.expectParam("width",width);
    }

    @Nullable
    public String hint() {
        return this.hint;
    }

    public int width() {
        return this.width;
    }

}
