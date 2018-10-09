package org.llaith.onyx.formkit.view.form.layout;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 04-Dec-2009
 * Time: 05:24:23
 */
public class FieldDependency {

    private final DependencyWidget dependentWidget;
    private final String dependentValue;

    public FieldDependency(final DependencyWidget dependentWidget, final String dependentValue) {
        this.dependentWidget = dependentWidget;
        this.dependentValue = dependentValue;
    }

    public DependencyWidget dependencyWidget() {
        return this.dependentWidget;
    }

    public String dependentValue() {
        return this.dependentValue;
    }

    public boolean isEnabled() {
        if (this.dependentWidget.currentValue() == null) return false;
        return this.dependentWidget.currentValue().equals(this.dependentValue);
    }
}
