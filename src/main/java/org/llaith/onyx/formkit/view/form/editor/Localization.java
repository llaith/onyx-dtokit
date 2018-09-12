package org.llaith.onyx.formkit.view.form.editor;


import java.text.DateFormat;
import java.text.NumberFormat;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Sep 8, 2008
 * Time: 11:30:45 PM
 *
 * DESIGN: THIS IS NOT A GENERAL LOCALIZATION FACTORY! It is APPKIT'S one only!
 * It could be modified to take a standard one if required later!
 *
 * TODO: as we can't use this outside of GWT, we need a way to pass the settings on to other external
 * locale managers. First step is including the locale code (we cannot use the Locale class inside GWT). Another
 * is to create a class with a subset of config settings (like date patterns) - and bring that into this and the
 * external ones.
 *
 * TODO: This class originally existed because GWT uses it's own DateTimeFormat classes... is it still required????
 *
 */
public class Localization {

    public DateFormat dateFormatFor( final DateFieldMode mode) {
        switch (mode) {
            case Date:
                return DateFormat.getDateInstance(DateFormat.SHORT);
            case Time:
                return DateFormat.getDateInstance(DateFormat.SHORT);
            case DateTime:
                return DateFormat.getDateInstance(DateFormat.SHORT);
            default:
                return DateFormat.getDateInstance(DateFormat.SHORT);
        }
    }

    public NumberFormat integerFormat() {
        // return NumberFormat.getFormat("#,###;-#,###");
        return NumberFormat.getIntegerInstance();
    }

    public NumberFormat numberFormatFor( final DecimalFieldMode mode) {
        switch (mode) {
            case Normal:
                return NumberFormat.getNumberInstance();
            case Currency:
                return NumberFormat.getCurrencyInstance(); // (old) put getCurrencyCode as one of these methods
            case Percent:
                return NumberFormat.getPercentInstance();
            case Scientific:
                return NumberFormat.getNumberInstance(); // TODO: create scientific format
            case Engineering:
                return NumberFormat.getNumberInstance(); // TODO: create engineering format
            default:
                return NumberFormat.getNumberInstance();
        }
    }

}
