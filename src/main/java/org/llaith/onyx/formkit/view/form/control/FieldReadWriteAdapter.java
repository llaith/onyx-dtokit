/*
 * Created on 31/05/2006
 */
package org.llaith.onyx.formkit.view.form.control;


import org.llaith.onyx.formkit.view.presenter.control.GivesValue;

/**
 * Notes:
 * I used to pass the previous 'lastValue' from the getValue() call back into getValue. It was something about Lists. I
 * don't know why now.
 * In future be aware that you MUST NOT use lists or sets in Fields. Instead create an IMMUTABLE ValueCollection holdre
 * object!
 *
 */
public interface FieldReadWriteAdapter<T> extends FieldReadOnlyAdapter<T>, GivesValue<T> {

    public boolean hasEntry(); // used for requiredSets - if we have this we don't need peekValues
                                        // (this IS a peek value and it's neater logic)

    public void clearError(); // for advanced view techs!

    public void setError(String message); // I think this is a cleaner design then using the form to
                                                     // map it. That option is still available.

    public void setDirty(boolean dirty);

    public void setMerged(boolean merged);

    public void setConflicted(boolean conflicted);

}
