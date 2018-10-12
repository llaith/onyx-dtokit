/*
 * Created on 31/05/2006
 */
package org.llaith.onyx.formkit.view.form.control;


import org.llaith.onyx.formkit.dto.Dto;
import org.llaith.onyx.formkit.dto.validation.Property;
import org.llaith.onyx.formkit.dto.validation.PropertyValidationException;
import org.llaith.onyx.formkit.view.form.layout.PropertyWidget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;


/**
 * BREAKTHROUGH: Fri, 20th Nov, 2009 (at 3:30am - 6:30am)
 *
 * @author nosd
 *         In the case of hard coded labels we set an initial peekValue, and put only a viewWriteAdapter in which should
 *         cause the same value
 *         to be pushed back to the screen each time - effectively making it a constant!. Cool!
 *         <p/>
 *         DESIGN: The modelAdapter can no longer be null. It should always be a DTO.
 *         <p/>
 *         Inactive fields MUST clear the value in the peek AND the BaseModel (DTO) otherwize controls which cannot be
 *         blank (send nulls) like Checkboxes and Number Spinners will always put a value into the DTO (bad for edits
 *         where such a field is changed). This means we cannot send an error message saying 'field must be blank'
 *         unless that comes from the view (Dialog) before the form.toModel() is called. This seems correct because in
 *         the case of GXT then it can blank the fields dynamically so such things are not required. The small chance
 *         that users will be suprised by the values being cleared can be accompanied by an immediate showing of the
 *         'view' form so they can see the values went away.
 *         <p/>
 *         Note that if we display the form as a Wizard, once again we don't need the error about 'field should be
 *         blank' as the field would never be presented to them.
 *         <p/>
 *         Perhaps another mode can be added to the form - a performPreScan() funciton that uses pre-validators to check
 *         the fields? Seems alot of trouble for a small benefit. Considering even in struts you could use javascript to
 *         clear the disabled fields automatically!
 *
 *         Critical! The idea is this. When we toModel, we clear the field errors and rechack etc purely from the field
 *         onwards. When we toView, we OVERRIDE the view errors etc. This is because very dynamic views (GXT) need synch
 *         to the checks we do in the model that we don't do on the screen (eg. required checks)
 *
 *
 *         NEW IDEA: Once the neo 'dialog' is done, try to replace ViewAdapters with events. Or another approach like
 *         the ModelAdapter
 *
 *         We pull the value when we toScreen, to if we forget to validate we'll tend to put the same value back.
 *
 *         NOTE: Standard adapter will fromView and Validate then possibly have errors. So the next toView should check
 *         for errors and display them.
 *
 *         NOTE: New Lifecycle is: FromModel ToView FromView Validate if errors -> ToView FromView Validate ToModel
 *
 *         BTW, if i can go import -> validate that solves all my problems with dto's cause then they can be dumb
 *
 *         Update: There should only be 1 activeGroup (FieldGroup) per field (because nesting them just adds), and also
 *         one required group (cause they're mutually exlusive) which is always going to be a subset of the FieldGroup.
 */
public class Field<T> {

    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(Field.class);

    // immutable state

    private final PropertyWidget<T> control;
    private final Property<T> property;
    private final String propertyName;

    // attachable state
    @Nullable
    private FieldReadOnlyAdapter<T> roViewAdapter = null;
    @Nullable
    private FieldReadWriteAdapter<T> rwViewAdapter = null;

    private Dto dto;

    // exportable state
    @Nullable
    private String error = null;

    // MUST start false, the model is correct and toView called first.
    private boolean conversionOk = false;

    public Field( final PropertyWidget<T> control) {
        super();
        this.control = control;
        this.property = control.property();
        this.propertyName = this.property.id().name();
    }

    public void setViewAdapter(final FieldReadOnlyAdapter<T> adapter) {
        this.roViewAdapter = adapter;
        if (adapter instanceof FieldReadWriteAdapter) this.rwViewAdapter = (FieldReadWriteAdapter<T>)adapter;
        else this.rwViewAdapter = null; // catches reset to null and non-instances
    }

    public Property<T> property() {
        return this.property;
    }

    public void attachDto(final Dto dto) {
        this.resetState();
        this.dto = dto;
    }

    @SuppressWarnings("unchecked")
    public void toView() {

        if ((this.rwViewAdapter != null) && (this.dto != null)) {
            this.rwViewAdapter.setDirty(this.dto.dirty(this.propertyName));
            this.rwViewAdapter.setMerged(this.dto.updated(this.propertyName));
            this.rwViewAdapter.setConflicted(this.dto.conflicted(this.propertyName));

            if (this.error != null) this.rwViewAdapter.setError(this.error);
            else this.rwViewAdapter.clearError();
        }

        // This should never be null (but silence is better than crash)
        if (this.roViewAdapter != null) {
            if (!this.enabled()) this.roViewAdapter.clearValue();
            else if (this.conversionOk) {
                if (!this.dto.has(this.propertyName)) this.roViewAdapter.clearValue();
                else this.roViewAdapter.setValue((T)this.dto.get(this.propertyName));
            }
        }
    }

    public void fromView() {
        if ((this.rwViewAdapter != null) && (this.writable())) {

            this.conversionOk = false;

//            try {
                if (this.rwViewAdapter.hasEntry()) {
                    this.dto.set(this.propertyName,this.rwViewAdapter.getValue());
                    this.conversionOk = true;
                } else {
                    this.dto.set(this.propertyName,null);
                }
                this.error = this.validate();
//            }
//            catch (final FieldValueException e) {
//                this.error = "The entered currentValue is not a valid " + this.property.id().type().getName();
//            }
        }
    }

    private void resetState() {
        this.error = null;
        this.conversionOk = true;
    }

    public boolean hasError() {
        return (this.error == null);
    }

    @Nullable
    public String error() {
        return this.error;
    }

    private boolean enabled() {
        return this.control.dependecy().isEnabled();
    }

    private boolean writable() {
        return this.control.access() == PropertyWidget.Access.READ_WRITE;
    }

    @Nullable
    private String validate() {
        if (!this.enabled()) return null;

        if (this.dto.has(this.propertyName)) { return this.validateRequired(); }

        return this.validateErrors();
    }

    @Nullable
    private String validateRequired() {
        if (!this.dto.has(this.propertyName)) {
            if (this.property.required()) this.error = "is required";
        }
        return null;
    }

    @Nullable
    private String validateErrors() {
        try {
            final Object val = this.dto.get(this.propertyName);
            if (val != null) this.property.validate(val);
        }
        catch ( final ClassCastException e) {
            return e.getMessage();
        }
        catch ( final PropertyValidationException e) {
            return e.getMessage();
        }
        return null;
    }

    public void pushError(final String error) {
        this.error = error;
        if (this.rwViewAdapter != null) {
            this.rwViewAdapter.setError(error);
        }
    }

}
