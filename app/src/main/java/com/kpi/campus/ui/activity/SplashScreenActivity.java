package com.kpi.campus.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.kpi.campus.R;
import com.kpi.campus.util.InternetBroadcastReceiver;

public class SplashScreenActivity extends AppCompatActivity {

    private IntentFilter ifInternetCheck;
    private InternetBroadcastReceiver ibrInternetCheck;
    private boolean noInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setCheckingInternet();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (noInternet){
                    checkInternet();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void checkInternet() {
        if (InternetBroadcastReceiver.getConnectivityStatus(getApplicationContext())
                == InternetBroadcastReceiver.TYPE_NOT_CONNECTED) {
            noInternet = true;
        } else {
            noInternet = false;
            SplashScreenActivity.this
                    .startActivity(new Intent(SplashScreenActivity
                            .this.getApplicationContext(), MainActivity.class));
            SplashScreenActivity.this.finish();
        }
    }

    private void setCheckingInternet() {
        noInternet = true;
        ibrInternetCheck = new InternetBroadcastReceiver();
        ifInternetCheck = new IntentFilter();
        ifInternetCheck.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(ibrInternetCheck, ifInternetCheck);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(ibrInternetCheck);
        super.onDestroy();
    }
}
