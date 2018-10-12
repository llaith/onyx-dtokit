package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import com.llaith.dto.Dto;
import org.llaith.onyx.formkit.dto.validation.Property;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 18, 2008
 * Time: 8:01:47 AM
 * <p/>
 * DESIGN:
 * We have primitives : DateTime, Number, String and Boolean ; and we have
 * References : ModelData.
 * Of those, a boolean dropdown always has the same values, a number field is
 * unlikely to have missing values between it's min and max and
 * therefore is better served with a 'numberField' and the entry of valid
 * DateTime values is so painful that it's best done with a CalendarControl.
 * <p/>
 * Also, ModelData cannot be created with combo text entry. This leaves us with
 * 3 different items.
 * <p/>
 * 1) A Dropdown control that uses a DataKey to get it's ModelData instances,
 * and
 * 2) A combo which is string based. Perfect for title. Preset list but can
 * enter others.
 * 3) A list box with multiselect. This may be better as a ReferenceBrowser?
 * <p/>
 * Multiselect list (one lb with checkboxes or 2 lib with arrows) Only strings -
 * Refs use Browsers.
 * Single select (usually a drop down)
 * Suggestion/Combobox (either a dropdown+tf or google suggest style)
 * <p/>
 * NOTE: This exists separate from ComboBox because ComboBoxes are String only
 * and can therefore have edits, wheras
 * this one *can* be a formDto one and therefore cannot. Also this is the one
 * that can have grouping.
 * <p/>
 * We leave the resitrict to selection because the combobox is text it makes
 * sense to try to build a searchable one
 * (or autosuggest style) when restrict-to-selection is true.
 */
public class DropdownWidget<T> extends PropertyWidget<T> {


    public static abstract class DropdownFieldAdapter<T> extends FieldAdapter<T> {

        public DropdownFieldAdapter(final Property<T> property) {
            super(property);
        }
    }

    public static class DropdownStringFieldAdapter extends DropdownFieldAdapter<String> {

        public DropdownStringFieldAdapter(final Property<String> property) {
            super(property);
        }

    }

    public static class DropdownDtoFieldAdapter extends DropdownFieldAdapter<Dto> {

        public DropdownDtoFieldAdapter(final Property<Dto> property) {
            super(property);
        }

    }

    public DropdownWidget(final String fieldLabel, final int order, final DropdownFieldAdapter<T> fieldAdapter, final Access access) {
        super(fieldLabel, order, fieldAdapter, access);
    }

}
