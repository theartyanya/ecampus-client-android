package com.kpi.campus.ui.presenter;

import javax.inject.Inject;

/**
 * Created by Admin on 02.02.2016.
 */
public class Bb1TabPresenter extends BasePresenter {

    private IView mView;

    @Inject
    public Bb1TabPresenter() {
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public interface IView {
        void setViewComponent();
    }
}
