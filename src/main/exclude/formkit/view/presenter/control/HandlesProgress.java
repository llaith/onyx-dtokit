package org.llaith.onyx.formkit.view.presenter.control;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 13/01/2012
 * Time: 20:53
 */
public interface HandlesProgress {

    public interface ProgressHandler {
        void handleStart(int total);
        void handleFinish();
        void handleSuccess();
        void handleFailure();
        void handleCancel();
        void handleProgress(int current, int total);
    }


    void setProgressHandler(ProgressHandler handler);

}
