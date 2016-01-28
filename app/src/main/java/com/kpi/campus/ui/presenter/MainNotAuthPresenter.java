package com.kpi.campus.ui.presenter;

import com.kpi.campus.ui.activity.MainNotAuthActivity;

import javax.inject.Inject;

/**
 * Created by Administrator on 28.01.2016.
 */
public class MainNotAuthPresenter extends BasePresenter {

    private IView mView;

    @Inject
    public MainNotAuthPresenter() {

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
