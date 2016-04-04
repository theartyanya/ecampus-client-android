package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.kpi.campus.ui.Navigator;
import com.kpi.campus.receiver.InternetBroadcastReceiver;

import javax.inject.Inject;

/**
 * Created by Nikita on 01.04.2016.
 */
public class SplashScreenPresenter extends BasePresenter {
    private  Context mContext;
    private IView mView;
    private Navigator mNavigator;
    private IntentFilter ifInternetCheck;
    private InternetBroadcastReceiver ibrInternetCheck;
    private boolean noInternet;

    @Inject
    public SplashScreenPresenter(Navigator navigator, Context context) {
        mNavigator = navigator;
        mContext = context;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }


    public void startCheckingInternet() {
        //every 1 second check connection
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

    public void checkInternet() {
        if (InternetBroadcastReceiver.getConnectivityStatus(mContext)
                == InternetBroadcastReceiver.TYPE_NOT_CONNECTED) {
            noInternet = true;
        } else {
            //Internet connection is ON
            noInternet = false;
            mNavigator.startLoginActivity();
        }
    }

    public void setCheckingInternet() {
        //set all stuff for checking Internet
        noInternet = true;
        ibrInternetCheck = new InternetBroadcastReceiver();
        ifInternetCheck = new IntentFilter();
        ifInternetCheck.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //start checking Internet connection
        mContext.registerReceiver(ibrInternetCheck, ifInternetCheck);
    }


    public interface IView {
        void checkInternet();
        void setCheckingInternet();
        void startCheckingInternet();
        void setViewComponent();
    }
}
