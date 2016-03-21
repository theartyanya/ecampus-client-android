package com.kpi.campus.ui.activity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.kpi.campus.Config;
import com.kpi.campus.R;
import com.kpi.campus.api.response.BaseResponse;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.loader.TokenLoader;
import com.kpi.campus.model.pojo.Token;
import com.kpi.campus.ui.presenter.LoginPresenter;
import com.kpi.campus.util.ToastUtil;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Login activity.
 */
public class LoginActivity extends BaseActivity implements LoginPresenter.IView, LoaderManager.LoaderCallbacks<BaseResponse> {

    @Bind(R.id.edit_text_input_login)
    EditText mInputLogin;
    @Bind(R.id.edit_text_input_password)
    EditText mPassword;
    @Bind(R.id.button_login)
    Button mBtnLogin;
    @Inject
    LoginPresenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkUserIsLogged();
    }

    @Override
    protected List<Object> getModules() {
        LinkedList<Object> modules = new LinkedList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public void showLoginProgressDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.progress_authenticate));
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showLoginButton(boolean shouldShow) {
        mBtnLogin.setActivated(shouldShow);
    }

    @Override
    public void onLoginFailed() {
        ToastUtil.showShortMessage(getString(R.string.login_failed), getApplicationContext());
    }

    @Override
    public void initLoader(Bundle args) {
        getLoaderManager().initLoader(R.id.api_loader, args, this);
    }

    @OnClick(R.id.button_login)
    public void login() {
        mPresenter.login(mInputLogin.getText().toString(), mPassword.getText().toString());
    }

    @Override
    public Loader<BaseResponse> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case R.id.api_loader:
                String login = bundle.getString(Config.KEY_LOGIN);
                String pass = bundle.getString(Config.KEY_PASSWORD);
                return new TokenLoader(getApplicationContext(), login, pass);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<BaseResponse> loader, BaseResponse baseResponse) {
        int id = loader.getId();
        if (id == R.id.api_loader) {
            Token token = baseResponse.getTypedAnswer();
            mPresenter.setLoaderResult(token);
        }
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(Loader<BaseResponse> loader) {

    }
}
