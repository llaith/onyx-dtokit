package org.llaith.onyx.formkit.view.form.layout;


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.*;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 17, 2008
 * Time: 3:07:58 PM
 * <p/>
 * Notes:
 * Since 'dependency' was from the DTO to the control, we only need 'string'
 * values as we only ever match them and never put them anywhere.
 * <p/>
 * Perhaps putting *1* value in this should be represented as a checkbox?
 */
public class DependencyWidget extends FieldWidget {

    private final List<String> allValues = new ArrayList<>();
    @Nullable
    private String currentValue = null;

    public DependencyWidget(final String fieldLabel, final int order, final String... values) {
        super(fieldLabel, order);
        this.allValues.addAll(Arrays.asList(values));
    }


    public List<String> allValues() {
        return this.allValues;
    }

    public void setCurrentValue(final String value) {
        if (!this.allValues.contains(value)) throw new RuntimeException(format(
                "Dependecy value: %s is missing from allowed list: %s",value, this.allValues));
        this.currentValue = value;
    }

    @Nullable
    public String currentValue() {
        return this.currentValue;
    }

}
