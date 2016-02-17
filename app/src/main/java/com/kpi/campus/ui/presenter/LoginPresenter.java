package com.kpi.campus.ui.presenter;

import android.os.Bundle;
import android.os.Handler;

import com.kpi.campus.model.pojo.Token;
import com.kpi.campus.ui.Navigator;
import com.kpi.campus.ui.activity.LoginActivity;

import javax.inject.Inject;

/**
 * LoginPresenter created to manage LoginActivity.
 *
 * Created by Administrator on 29.01.2016.
 */
public class LoginPresenter extends BasePresenter {

    private IView mView;
    private Navigator mNavigator;
    private Token mAuthToken;

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
        mView.showLoginButton(false);

        startValidation(login, password);

        /// TODO: rewrite this method
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        mView.dismissProgressDialog();
                    }
                }, 3000);

        mView.showLoginButton(true);
        if(mAuthToken==null) {
            mView.onLoginFailed();
        } else {
            mNavigator.startMainActivity();
        }
    }

    private void startValidation(String login, String password) {
        Bundle args = new Bundle();
        args.putString(LoginActivity.KEY_LOGIN, login);
        args.putString(LoginActivity.KEY_PASSWORD, password);
        mView.initLoader(args);
    }

    public void setLoaderResult(Token token) {
        mAuthToken = token;
    }

    public interface IView {
        void setStringResources();
        void showLoginProgressDialog();
        void dismissProgressDialog();
        void showLoginButton(boolean shouldShow);
        void onLoginFailed();
        void initLoader(Bundle args);
    }
}
