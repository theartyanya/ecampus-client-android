package com.kpi.campus.ui.presenter;

import android.os.Bundle;

import com.kpi.campus.ui.Navigator;
import com.kpi.campus.ui.activity.LoginActivity;

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
        mView.setStringResources();
    }

    public void login(String login, String password) {
        mView.showLoginProgressDialog();

        boolean isValid = validate(login, password);
        mView.dismissProgressDialog();

        if(!isValid) {
            mView.onLoginFailed();
        } else {
            mNavigator.startMainActivity();
        }
    }

    private boolean validate(String login, String password) {
        Bundle args = new Bundle();
        args.putString(LoginActivity.KEY_LOGIN, login);
        args.putString(LoginActivity.KEY_PASSWORD, password);
        mView.initLoader(args);
        return false;
    }

    public interface IView {
        void setStringResources();
        void showLoginProgressDialog();
        void dismissProgressDialog();
        void onLoginFailed();
        void initLoader(Bundle args);
    }
}
