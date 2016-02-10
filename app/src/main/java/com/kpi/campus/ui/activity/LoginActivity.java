package com.kpi.campus.ui.activity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;
import android.widget.EditText;

import com.kpi.campus.R;
import com.kpi.campus.api.response.BaseResponse;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.loader.TokenLoader;
import com.kpi.campus.model.Token;
import com.kpi.campus.ui.presenter.LoginPresenter;
import com.kpi.campus.util.ToastUtil;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginPresenter.IView, LoaderManager.LoaderCallbacks<BaseResponse> {

    @Bind(R.id.edit_text_input_login)
    EditText mInputLogin;
    @Bind(R.id.edit_text_input_password)
    EditText mPassword;
    @Inject
    LoginPresenter mPresenter;
    private ProgressDialog mProgressDialog;

    public static String KEY_LOGIN;
    public static String KEY_PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    protected List<Object> getModules() {
        LinkedList<Object> modules = new LinkedList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public void setStringResources() {
        KEY_LOGIN = getString(R.string.key_login);
        KEY_PASSWORD = getString(R.string.key_password);
    }

    @Override
    public void showLoginProgressDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.progress_authenticate));
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onLoginFailed() {
        ToastUtil.showShortMessage("Login failed", getApplicationContext());
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
                String s1 = bundle.getString(KEY_LOGIN);
                String s2 = bundle.getString(KEY_PASSWORD);
                return new TokenLoader(getApplicationContext(), s1, s2);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<BaseResponse> loader, BaseResponse baseResponse) {
        int id = loader.getId();
        if (id == R.id.api_loader) {
            List<Token> airports = baseResponse.getTypedAnswer();
            //do something here
        }
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(Loader<BaseResponse> loader) {

    }

}
