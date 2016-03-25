package com.kpi.campus.ui.presenter;

import android.os.Bundle;

import com.kpi.campus.Config;
import com.kpi.campus.model.pojo.User;
import com.kpi.campus.model.pojo.Token;
import com.kpi.campus.rx.UserRxLoader;
import com.kpi.campus.ui.Navigator;
import com.kpi.campus.ui.Preference;

import javax.inject.Inject;

/**
 * LoginPresenter created to manage LoginActivity.
 *
 * Created by Administrator on 29.01.2016.
 */
public class LoginPresenter extends BasePresenter {

    private IView mView;
    private Navigator mNavigator;
    private Preference mPreference;


    @Inject
    public LoginPresenter(Navigator navigator, Preference preference) {
        mNavigator = navigator;
        mPreference = preference;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
    }

    /**
     * Set necessary View components to "login state"
     * @param login login which is entered by the user
     * @param password password which is entered by the user
     */
    public void login(String login, String password) {
        mView.showLoginProgressDialog();
        mView.showLoginButton(false);
        validateUser(login, password);
    }

    /**
     * Init loader to load data for the user authentication.
     * @param login
     * @param password
     */
    private void validateUser(String login, String password) {
        Bundle args = new Bundle();
        args.putString(Config.KEY_LOGIN, login);
        args.putString(Config.KEY_PASSWORD, password);
        mView.initLoader(args);
    }

    /**
     * Set views to "initial (after login) state".
     * If server returns success, start MainActivity.
     * If not, launch login failed logic.
     * @param token data for the user authentication
     */
    public void setLoaderResult(Token token) {
        mView.dismissProgressDialog();
        mView.showLoginButton(true);
        if (token != null) {
            onLoginSuccess(token.getAccessToken());
        } else {
            mView.onLoginFailed();
        }
    }

    private void onLoginSuccess(String token) {
        saveToken(token);
        loadInfoAboutUser();
        mNavigator.startMainActivity();
    }

    public void saveToken(String token) {
        saveTokenToUserModel(token);
        saveStateToPref(token);
    }

    /**
     * Save login values to SharedPreferences.
     */
    private void saveStateToPref(String tokenValue) {
        mPreference.saveLoginInfo(tokenValue);
    }

    private void saveTokenToUserModel(String token) {
        User.getInstance().token = token;
    }

    private void loadInfoAboutUser() {
        UserRxLoader loader = new UserRxLoader();
        loader.apiCall();
    }

    public interface IView {
        void showLoginProgressDialog();
        void dismissProgressDialog();
        void showLoginButton(boolean shouldShow);
        void onLoginFailed();
        void initLoader(Bundle args);
    }
}
