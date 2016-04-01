package com.kpi.campus.ui.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.WindowManager;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.ui.presenter.SplashScreenPresenter;
import com.kpi.campus.util.InternetBroadcastReceiver;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class SplashScreenActivity extends BaseActivity implements SplashScreenPresenter.IView {

    private IntentFilter ifInternetCheck;
    private InternetBroadcastReceiver ibrInternetCheck;
    private boolean noInternet;
    @Inject
    SplashScreenPresenter ssPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        bindViews();
        ssPresenter.setView(this);
        ssPresenter.initializeViewComponent();
        setCheckingInternet();
        startCheckingInternet();
    }

    @Override
    protected List<Object> getModules() {
        //hz che ono delaet, no bez etogo not work
        LinkedList<Object> modules = new LinkedList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public void checkInternet() {
        ssPresenter.checkInternet();
    }

    @Override
    public void setCheckingInternet() {
        ssPresenter.setCheckingInternet();
    }

    @Override
    public void startCheckingInternet() {
        ssPresenter.startCheckingInternet();
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
        unregisterReceiver(ibrInternetCheck);
        super.onDestroy();
    }
}
