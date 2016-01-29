package com.kpi.campus.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.ui.presenter.LoginPresenter;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginPresenter.IView {

    @Bind(R.id.edit_text_input_login)
    EditText mInputLogin;
    @Bind(R.id.edit_text_input_password)
    EditText mPassword;
    @Bind(R.id.button_login)
    Button mBtnLogin;
    @Inject
    LoginPresenter mPresenter;

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
    public void setViewComponent() {

    }

    @OnClick(R.id.button_login)
    public void login() {
        mPresenter.login(mInputLogin.getText().toString(), mPassword.getText().toString());
    }
}
