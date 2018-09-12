package org.llaith.onyx.formkit.view.form.layout.profile.basic;


import com.llaith.dto.Dto;
import org.llaith.onyx.formkit.dto.validation.Property;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Aug 2, 2008
 * Time: 8:41:06 PM
 * <p/>
 * Implement with Apache commons.primitives's StrSubstitutor
 */
public class LabelWidget<T> extends PropertyWidget<T> {


    public static final String VALUE_PARAM = "value";

    public static abstract class LabelFieldAdapter<T> extends FieldAdapter<T> {

        public LabelFieldAdapter(final Property<T> property) {
            super(property);
        }
    }

    public static class LabelBooleanFieldAdapter extends LabelFieldAdapter<Boolean> {

        public LabelBooleanFieldAdapter(final Property<Boolean> property) {
            super(property);
        }
    }

    public static class LabelDateFieldAdapter extends LabelFieldAdapter<Date> {

        public LabelDateFieldAdapter(final Property<Date> property) {
            super(property);
        }
    }

    public static class LabelDecimalFieldAdapter extends LabelFieldAdapter<BigDecimal> {

        public LabelDecimalFieldAdapter(final Property<BigDecimal> property) {
            super(property);
        }
    }

    public static class LabelDtoFieldAdapter extends LabelFieldAdapter<Dto> {

        public LabelDtoFieldAdapter(final Property<Dto> property) {
            super(property);
        }
    }

    public static class LabelIntegerFieldAdapter extends LabelFieldAdapter<BigInteger> {

        public LabelIntegerFieldAdapter(final Property<BigInteger> property) {
            super(property);
        }
    }

    public static class LabelStringFieldAdapter extends LabelFieldAdapter<String> {

        public LabelStringFieldAdapter(final Property<String> property) {
            super(property);
        }

    }


    private final String paramText;
    private final String emptyText;

    public LabelWidget(final String fieldLabel, final int order, final LabelFieldAdapter<T> fieldAdapter, final @Nullable String paramText,
                       final @Nullable String emptyText) {
        super(fieldLabel, order, fieldAdapter, Access.READ_ONLY);

        this.paramText = paramText == null ? "${" + LabelWidget.VALUE_PARAM + "}" : paramText;
        this.emptyText = emptyText;
    }


    public String paramText() {
        return this.paramText;
    }

    public String getEmptyText() {
        return this.emptyText;
    }
}
