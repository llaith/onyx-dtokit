package org.llaith.onyx.formkit.view.presenter;

import org.llaith.onyx.formkit.controller.Controller;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 16-Dec-2009
 * Time: 21:18:48
 *
 * Desgin Notes:
 *
 *      1) This is designed to be subsclassed as either specific presenters,
 *      eg: ClientDetailsPresenter, and/or as GUI Patterns 'WizardPresenter'.
 *
 *      2) Presenter does not abstract away the entire view. It only abstracts
 *      away the interface between the view and the model. It also abstracts
 *      away the handlers used for getting data for the view components and
 *      the navigation. My WidgetToolkit is used to abstract away the view
 *      almost completely.
 *
 *      3) The implementing class should define an interface for V. This V is made
 *      up of all the presenter.controls. The implementing class should have one
 *      adapter per function of the widget. When the widget has multiple adapters
 *      of the same type, you need to have more than one adapter per widget. Eg
 *
 *      interface LoginView {
 *          GivesValue<Sting> login;
 *          GivesValue<Sting> password;
 *      }
 *
 *      // pretend loginDialog is a single widget with two fields
 *      bindView(LoginView view) {
 *          view.login = vaadinLoginDialogAdapterBuilder.loginBoxAdapter(vaadinLoginDialog);
 *          view.password = vaadinLoginDialogAdapterBuilder.passwordBoxAdapter(vaadinLoginDialog);
 *      }
 *
 *
 */
public interface Presenter<V,M> extends Controller {

    void bindView(V view);

    void bindModel(M model);

    void refreshModel(); // used when data has changed

    void updateView(); // used when view has been dynamically updated (eg, added a control)

}
