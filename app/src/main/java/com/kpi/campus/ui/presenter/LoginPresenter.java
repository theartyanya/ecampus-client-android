package com.kpi.campus.ui.presenter;

import com.kpi.campus.ui.Navigator;

import javax.inject.Inject;

/**
 * Created by Administrator on 29.01.2016.
 */
public class LoginPresenter extends BasePresenter {

    private IView mView;
    private Navigator mNavigator;

    @Inject
    public LoginPresenter(Navigator navigator) {
        mNavigator = navigator;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public void login(String login, String password) {

    }

    private void openMain() {
        mNavigator.startMainActivity();
    }

    public interface IView {
        void setViewComponent();
    }
}
