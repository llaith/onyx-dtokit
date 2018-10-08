package org.llaith.onyx.formkit.view.form.control;


import org.llaith.onyx.formkit.dto.Dto;
import org.llaith.onyx.formkit.dto.validation.PropertySetValidationException;
import org.llaith.onyx.formkit.dto.validation.PropertySetValidator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 21, 2008
 * Time: 2:42:38 PM
 *
 *
 * Can be used as a presentable (automatic mode) or as a manually.
 *
 *
 *
 * Note: There are no such things as context errors. Any context error comes from field input and therefore the fields
 * in question can display the relevant error message.
 *
 * Now in the case of the idea of post 'form' processing data model errors that come from the webapp. That is a
 * different issue.
 *
 * That is most likely to come from some custom controller code (not worth trying to put into 'validators' - especially
 * given the
 * remote nature of the checking) and displayed somehow on the form but not part of the automatic methods.
 *
 * Merged from Dto
 *
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: Jul 31, 2008
 * Time: 5:20:54 PM
 *
 * Oct 30,2009:
 * I *know* now what went wrong with this. If the Dto/DtoType is generic then you do not have the full type signature
 * until you have extended it for your applications classes, eg UserDto extends Dto<UserType>. The HUGE problem with
 * this
 * that has a myriad of manifestations is that you cannot really work with a 'standard profile' of controls as the final
 * objects
 * that take the Dto's need typing to. So you need to create a UserProperty and a UserId etc. All very nice but not how
 * the
 * primitives is supposed to work. So the formlib needs to be a 'closed' generic primitives. The other this is that all
 * this typing, in
 * the end, buys you NOTHING because YOU have to remember to use the correct type with the Dto. You *could* go like this
 * Boolean b = userDto.get(NotUserDtoType.SOME_BOOLEAN_PROPERTY) which would get null.
 *
 * This is just to unite mine with GXT so I can remove dependencies fro GXT in my code
 *
 * NOTES:
 * *) The 'T' is not required. It's just nice to have for built in forms. There is no reason why you cannot have a
 * Dto<UserForm> with 20 different types of UserForm and using the dynamic userForm.getId() approach.
 *
 * *) Unlike v1, there is no longer any 'field groups' or 'required field groups'. There is a simple rule instead. All
 * 'required' fields are relative to the Dto. If you want sets of independent requireds - split into more than one
 * Dto. As for the view side, there is NO RULE about how the Dto's are grouped on the screen in sections. That
 * is under the control of the Dialog/Control Builder - a separate library.
 *
 * *) Many of the 'form' logic from v1 has been moved to here for v2.
 *
 *
 * separation of concerns - dto takes values of correct type and can validate them. 'Lifecycle' and 'channel' -eg, web
 * page adapters
 * are a 'form' issue. You would have an xmlreader around the dto too - which wouldn't be a form btw, but would concern
 * itself
 * with conversion.
 *
 * So dto, holds one set (no buffer) - of correct type but possibly invalid values. It has no conversion routines or
 * adapters - it is
 * wrapped by things that need to put values in or out - such as xml readers or 'forms'. Forms have a 'lifecycle' or
 * submit/refresh.
 * On submit form reads all values into a buffer. This is to simplify enableds. A field that has an invalid value is
 * considered 'filled'
 * for the purposes of 'group' requireds, but obviously the value will not allow the enableds to be determined (wont
 * matter for displays
 * with checkboxes but will for cli etc. Something needs to write booleans as false. An optional boolean should be done
 * with an enabled
 * not a null. A dto keeps it's own errors. Merging? com.llaith.toolkit.i18n? Fair to say that the dto is data and
 * strings/errors would be com.llaith.toolkit.i18n'd before they
 * got there? What about validation errors? Validators get there errors from an com.llaith.toolkit.i18n file anyway.
 * Just remember no depend from
 * dto validators to formcontrollers as before.
 *
 * So the 'control' is read and consumed by the builder which creates a ControlImpl from whatever kit (VaadinKit) and
 * hooks up the
 * necessary adapters. The Form wraps the Dto and handles interfacing with the Control.
 *
 * tkt.dto
 * tkt.formlib.form (M - delegates to submodel (dto) for some functions)
 * tkt.formlib.control (V)
 * tkt.formlib.controller (C)
 * tkt.formkit.vaadin (V)
 * tkt.formkit.cli (V)
 * tkt.xml.dto
 *
 *
 * // it will call to then and say - parent.isEnabled() && parent.isRequired() etc. Forms can be flat (after all the
 * screen is flat - nesting on
 * // the screen is navigation!) and therefore stage 1 is pull all the values from the screen adapters/controls into a
 * buffer. Once conversion
 * // is attempted then push to the dto. then call isValid on each field - yes - we still wrap each property in a field.
 * Fields pull the exceptions
 * // and errors out and put them into the field state. Fields also synch the controls (clearError etc) to the dto
 * journal. Therefore - field is
 * // CONTROLLER code. Dto is model and Controls are View. In order to know if
 * // something is enabled we need to have converted it. If we use enableds in the dto - as part of the model (better)
 * then we can put the
 * // enabled checks in the dto. When something is not enabled - we have to *clear* it! Still some questions...
 *
 * enableds can be 'flat' - a set of fields that are dependent on other fields in the same dto - or nested - dependent
 * on the 'parent' property
 * of the form.
 *
 * If your thinking... 'this seem asymetric?' well it is! Reason? One side is editable (probably) the other isn't.
 *
 *
 *
 * Here are two neo points,
 * 1) it's the IMMUTABLE objects which use the fieldName() style instead of getFieldName(). That's an easy way to tell
 * the difference. There is no
 * javabeaning of the immutable ones anyway - so the lack of a get... won't hurt.
 *
 * 2) The Form primitives works with atomic objects. The granularity is what needs to have a single validator. So the
 * forst example is a DateRange which
 * ends up being 2 fvalues in 1 field. The slight mismatch is to be dealt with in other ways. So in database terms the
 * UserForms are usually don via
 * a table like [Id,FormId,FieldName,FieldValue] anyway. The other ones are done via the ModelAdapters and in any case
 * SHOULD be the object -
 * remember hibernate can map objects like that anyway.
 *
 * 20110114:
 * Form is not a Presentable! The Presentable that holds the form sets it up, and also the other stuff like the
 * listbox selections etc.
 *
 * Remember forms and their DTO's may be dynamic, such as for criteria creation.
 *
 */
public class Form<T extends Dto> implements Serializable {


    private final Map<String,Field<?>> fields;
    private final Set<PropertySetValidator<T>> propertySetValidators = new HashSet<>();

    private final T dto;

    public Form(final T dto,  final Set<Field<?>> fields) {
        this.dto = dto;
        this.fields = this.indexFields(fields);
    }


    private Map<String,Field<?>> indexFields( final Set<Field<?>> fields) {
        final Map<String,Field<?>> map = new HashMap<>();
        for (final Field<?> f : fields) {
            map.put(f.property().id().name(),f);
        }
        return map;
    }

    public T dto() {
        return this.dto;
    }

    public void toView() {
        for (final Field<?> f : this.fields.values()) {
            f.toView();
        }
    }

    public void fromView() {
        for (final Field<?> f : this.fields.values()) {
            f.fromView();
        }
        this.validatePropertyGroups();
    }

    public boolean hasErrors() {
        for (final Field<?> f : this.fields.values()) {
            if (f.hasError()) return true;
        }
        return false;
    }


    public Map<String,String> errors() {
        final Map<String,String> errors = new HashMap<String,String>();
        for (final Field<?> f : this.fields.values()) {
            if (f.hasError()) errors.put(f.property().id().name(),f.error());
        }
        return errors;
    }

    private void validatePropertyGroups() {
        for (final PropertySetValidator<T> v : this.propertySetValidators) {
            try {
                v.validate(this.dto);
            }
            catch ( final PropertySetValidationException e) {
                if (this.propertySetOk(e.fields())) {
                    for (final String s : e.fields()) {
                        this.fields.get(s).pushError(e.error());
                    }
                }
            }
        }
    }

    private boolean propertySetOk( final String[] fields) {
        for (final String s : fields) {
            if (this.fields.get(s).hasError()) return false;
        }
        return true;
    }

}
