package org.llaith.onyx.formkit.dto.validation;


import org.llaith.onyx.formkit.dto.Dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 12-Sep-2009
 * Time: 21:19:20
 */
public class RequiredPropertyGroupValidator<T extends Dto> implements PropertySetValidator<T> {

    public enum Mode {
        EXACTLY_ONE,
        AT_LEAST_ONE,
        AT_MOST_ONE
    }

    private final Mode mode;

    private final String groupName;

    private final Set<Id> ids = new HashSet<Id>();

    public RequiredPropertyGroupValidator(final Mode mode, final String groupName, final Id[] ids) {
        this.mode = mode;
        this.groupName = groupName;
        this.ids.addAll(Arrays.asList(ids));
    }


    public Set<Id> ids() {
        return new HashSet<Id>(this.ids);
    }

    public Mode mode() {
        return this.mode;
    }

    public String groupName() {
        return this.groupName;
    }

    @Override
    public void validate( final T dto) throws PropertySetValidationException {

        boolean foundFirst = false;
        boolean foundMore = false;
        for (final Id id : this.ids) {
            if (dto.has(id.name())) {
                if (!foundFirst) foundFirst = true;
                else {
                    foundMore = true;
                    break;
                }
            }
        }

        switch (mode()) {
            case AT_LEAST_ONE: {
                if (!foundFirst) throw new PropertySetValidationException("At least one " + groupName() + " must be entered.");
            }
                break;
            case AT_MOST_ONE: {
                if (!foundMore) throw new PropertySetValidationException("At most one " + groupName() + " must be entered.");
            }
                break;
            case EXACTLY_ONE: {
                if (!foundFirst || foundMore) throw new PropertySetValidationException("Exactly one " + groupName() + " must be entered.");
            }
                break;
        }
    }

}
