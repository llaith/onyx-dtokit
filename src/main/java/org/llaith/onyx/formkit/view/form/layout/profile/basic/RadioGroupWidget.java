package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import com.llaith.dto.Dto;
import org.llaith.onyx.formkit.dto.validation.Property;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 06-Dec-2009
 * Time: 21:35:53
 */
public class RadioGroupWidget<T> extends PropertyWidget<T> {


    public static abstract class RadioGroupFieldAdapter<T> extends FieldAdapter<T> {

        public RadioGroupFieldAdapter(final Property<T> property) {
            super(property);
        }
    }

    public static class RadioGroupStringFieldAdapter extends RadioGroupFieldAdapter<String> {

        public RadioGroupStringFieldAdapter(final Property<String> property) {
            super(property);
        }
    }

    public static class RadioGroupDtoFieldAdapter extends RadioGroupFieldAdapter<Dto> {

        public RadioGroupDtoFieldAdapter(final Property<Dto> property) {
            super(property);
        }
    }

    public RadioGroupWidget(final String fieldLabel, final int order, final RadioGroupFieldAdapter<T> fieldAdapter, final Access access) {
        super(fieldLabel, order, fieldAdapter, access);
    }

}
