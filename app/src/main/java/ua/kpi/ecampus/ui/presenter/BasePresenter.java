package ua.kpi.ecampus.ui.presenter;

/**
 * Abstract presenter to work as base for every presenter created in the application.
 * This presenter declares some methods to attach the fragment/activity lifecycle.
 *
 * Created by Administrator on 28.01.2016.
 */
public abstract class BasePresenter {

    /**
     * Called when activity/fragment (view) must be initialized.
     */
    public abstract void initializeViewComponent();
}
