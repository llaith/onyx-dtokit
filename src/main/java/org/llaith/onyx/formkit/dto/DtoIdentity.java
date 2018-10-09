package org.llaith.onyx.formkit.dto;

import com.google.common.base.MoreObjects;
import org.llaith.onyx.toolkit.lang.Guard;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 *
 */
public class DtoIdentity {

    private final Object identityName;

    private final UUID instanceValue;

    private final Map<String,Object> identityValues;

    public DtoIdentity(final String identityName, final Map<String,Object> identityValues) {

        // create an identity from the IMMUTABLE identifying values.
        this.identityName = Guard.notNull(identityName);

        // assign the instance value (also used as identity if no other)
        this.instanceValue = UUID.randomUUID();

        // identity values
        this.identityValues = new HashMap<>(identityValues);

    }

    public Object getIdentityName() {
        return identityName;
    }

    public UUID getInstanceValue() {
        return instanceValue;
    }

    public Map<String,Object> getIdentityValues() {
        return Collections.unmodifiableMap(this.identityValues);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("identityName", identityName)
                          .add("instanceValue", instanceValue)
                          .add("identityValues", identityValues)
                          .toString();
    }

    @Override
    public boolean equals(final Object o) {
        return this.ourEquals(o);
    }

    @Override
    public int hashCode() {
        return this.ourHashCode();
    }

    private boolean ourEquals(final Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DtoIdentity that = (DtoIdentity)o;

        // return based on instance if instance is set
        if (this.identityValues.isEmpty()) {

            return Objects.equals(identityName, that.identityName) &&
                    Objects.equals(instanceValue, that.instanceValue);

        }

        // else return the one based on the values
        return Objects.equals(identityName, that.identityName) &&
                Objects.equals(identityValues, that.identityValues);

    }

    private int ourHashCode() {

        // return based on instance if instance is set
        if (this.identityValues.isEmpty()) return Objects.hash(identityName, instanceValue);

        // else return the one based on the values
        return Objects.hash(identityName, identityValues);

    }

}
