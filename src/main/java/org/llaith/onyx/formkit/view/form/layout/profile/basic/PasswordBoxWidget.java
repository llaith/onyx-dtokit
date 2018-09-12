package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import org.llaith.onyx.formkit.dto.validation.Property;
import com.llaith.util.lang.Guard;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 17, 2008
 * Time: 4:43:11 PM
 */
public class PasswordBoxWidget extends PropertyWidget<String> {


    public static class PasswordBoxFieldAdapter extends FieldAdapter<String> {

        public PasswordBoxFieldAdapter(final Property<String> property) {
            super(property);
        } // Note would have to extend Selections if add neo types
    }

    private final int width;

    @SuppressWarnings("boxing")
    public PasswordBoxWidget(final String fieldLabel, final int order, final PasswordBoxFieldAdapter fieldAdapter, final Access access, final int width) {
        super(fieldLabel, order, fieldAdapter, access);
        this.width = Guard.expectParam("width",width);
    }

    public int width() {
        return this.width;
    }

}
