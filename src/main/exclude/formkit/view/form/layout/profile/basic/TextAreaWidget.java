package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import org.llaith.onyx.formkit.dto.validation.Property;
import com.llaith.util.lang.Guard;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 17, 2008
 * Time: 5:46:01 PM
 * <p/>
 * There is a three level datatype relationship.
 * <p/>
 * TextAreaControl <- TextField <- String
 * <p/>
 * The string represents the data type.
 * The field represents the validation and other constraints
 * The control represents the display
 * <p/>
 * NOT the same thing as a rich tech. How about Html Text? Separate controls or
 * 'encoding' is optional?
 * If html or similar - can degrade to TextArea nicely!
 */
public class TextAreaWidget extends PropertyWidget<String> {


    public static class TextAreaFieldAdapter extends FieldAdapter<String> {

        public TextAreaFieldAdapter(final Property<String> property) {
            super(property);
        } // Note would have to extend Selections if add neo types
    }

    private final int width;
    private final int height;

    @SuppressWarnings("boxing")
    public TextAreaWidget(final String fieldLabel, final int order, final TextAreaFieldAdapter fieldAdapter, final Access access, final int width, final int height) {
        super(fieldLabel, order, fieldAdapter, access);
        this.width = Guard.expectParam("width",width);
        this.height = Guard.expectParam("height",height);
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

}
