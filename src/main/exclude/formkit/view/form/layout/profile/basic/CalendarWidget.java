package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import org.llaith.onyx.formkit.dto.validation.Property;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;

import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 18, 2008
 * Time: 10:16:31 AM
 * <p/>
 * TODO: NO! Old (pre2009) design. Factory should be smart enough to add
 * validator that wont allow the range to be backwards
 * TODO: Add businesssupport for timezones. May be also possible to have separate (just
 * timezone) mode or control. Same as currency
 */
public class CalendarWidget extends PropertyWidget<Date> {


    public static class CalendarFieldAdapter extends FieldAdapter<Date> {

        public CalendarFieldAdapter(final Property<Date> property) {
            super(property);
        }
    }

    // private boolean includeTimezone = false; // this shouldn't be in the
    // temporalfield mode. It's a different FieldModel with a different fieldId

    public CalendarWidget(final String fieldLabel, final int order, final CalendarFieldAdapter fieldAdapter, final Access access) {
        super(fieldLabel, order, fieldAdapter, access);
    }

}
