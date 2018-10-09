package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import org.llaith.onyx.formkit.dto.validation.Property;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;

import javax.annotation.Nullable;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 18, 2008
 * Time: 8:01:47 AM
 * <p/>
 * NOTE: we apply validation constraints to this one because unlike the dropdown
 * - if you have figures in the list that all fail
 * validation, we can still manually put something in using the text box portion
 * which will satisfy.
 * <p/>
 * TODO:
 * http://extjs.com/forum/showthread.php?t=36581&highlight=focus
 * <p/>
 * NOT ANY MORE!
 * <p/>
 * A combo with a restrict-to-selection (rts) is not exactly the same as a
 * dropdown. The combo assumes to many to display and may not be able
 * to display them all at once. A combo-rts would be a 'searchable' one in the
 * old days - or the suggestbox now.
 */
public class ComboTextWidget extends PropertyWidget<String> {


    // @formatter:off
    public static class ComboTextFieldAdapter extends FieldAdapter<String> {
        public ComboTextFieldAdapter(final Property<String> property) {
            super(property);
        } //Note would have to extend Selections if add neo types
    }

    public static class Selections {
        private final String selectionSourceId; // can be either a data-connected one or a SimpleSelection - Remember may be dynamic when combo-set's. A separate annotation can define an data use one
        private final boolean restrictToSelections; // if false then accepts freeform results - this will be variable on the technology - sometimes will result in two controls.

        public Selections(final String selectionSourceId) {
            this(selectionSourceId, true);
        }

        public Selections(final String selectionSourceId, final boolean restrictToSelections) {
            this.selectionSourceId = selectionSourceId;
            this.restrictToSelections = restrictToSelections;
        }
    }
    // @formatter:on

    private final Selections selections;

    public ComboTextWidget(final String fieldLabel, final int order, final ComboTextFieldAdapter fieldAdapter, final Access access, final @Nullable Selections selections) {
        super(fieldLabel, order, fieldAdapter, access);

        this.selections = selections;
    }

    public boolean hasSelections() {
        return this.selections != null;
    }

    @SuppressWarnings("synthetic-access")
    public boolean restrictToSelections() {
        return this.selections.restrictToSelections;
    }

    @SuppressWarnings("synthetic-access")
    public String selectionSourceId() {
        return this.selections.selectionSourceId;
    }
}
