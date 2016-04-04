package com.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.WindowManager;

import com.kpi.campus.Config;
import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.receiver.InternetBroadcastReceiver;
import com.kpi.campus.ui.presenter.SplashScreenPresenter;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class SplashScreenActivity extends BaseActivity implements SplashScreenPresenter.IView {

    @Inject
    SplashScreenPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
        setCheckingInternet();
        startCheckingInternet();
    }

    @Override
    protected List<Object> getModules() {
        LinkedList<Object> modules = new LinkedList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public void setCheckingInternet() {
        mPresenter.setCheckingInternet();
    }

    @Override
    public void startCheckingInternet() {
        mPresenter.startCheckingInternet();
    }

    @Override
    public void setViewComponent() {
        //hide all bars
        disableActionBar();
    }

    private void disableActionBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onDestroy() {
        InternetBroadcastReceiver receiver = mPresenter.getBroadcastReceiver();
        if(receiver != null) {
            try {
                unregisterReceiver(receiver);
            } catch (IllegalArgumentException e) {
                Log.d(Config.LOG, "Receiver is not registered.");
            }
        }
        super.onDestroy();
    }
}
