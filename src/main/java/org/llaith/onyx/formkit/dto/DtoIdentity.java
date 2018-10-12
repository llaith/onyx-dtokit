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

    private final Object dtoName;

    private final UUID instanceValue;

    private final Map<String,Object> identityValues;

    public DtoIdentity(final String dtoName, final Map<String,Object> identityValues) {

        // create an identity from the IMMUTABLE identifying values.
        this.dtoName = Guard.notNull(dtoName);

        // assign the instance value (also used as identity if no other)
        this.instanceValue = UUID.randomUUID();

        // identity values
        this.identityValues = new HashMap<>(identityValues);

    }

    public Object getDtoName() {
        return dtoName;
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
                          .add("dtoName", dtoName)
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

            return Objects.equals(dtoName, that.dtoName) &&
                    Objects.equals(instanceValue, that.instanceValue);

        }

        // else return the one based on the values
        return Objects.equals(dtoName, that.dtoName) &&
                Objects.equals(identityValues, that.identityValues);

    }

    private int ourHashCode() {

        // return based on instance if instance is set
        if (this.identityValues.isEmpty()) return Objects.hash(dtoName, instanceValue);

        // else return the one based on the values
        return Objects.hash(dtoName, identityValues);

    }

}
