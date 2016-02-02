package com.kpi.campus.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.kpi.campus.di.ActivityContext;
import com.kpi.campus.ui.activity.BulletinBoardActivity;
import com.kpi.campus.ui.activity.LoginActivity;
import com.kpi.campus.ui.activity.MainActivity;

import javax.inject.Inject;

/**
 * Class created to handle all the navigation between activities. This class knows how to open
 * every activity in the application and provides to the client code different methods to start
 * activities with the information needed.
 * Created by Administrator on 29.01.2016.
 */
public class Navigator {

    private Activity mActivityContext;

    @Inject
    public Navigator(@ActivityContext Context context) {
        mActivityContext = (Activity) context;
    }

    public void startLoginActivity() {
        Intent intent = getLaunchIntent(LoginActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startMainActivity() {
        Intent intent = getLaunchIntent(MainActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startBulletinBoardActivity() {
        Intent intent = getLaunchIntent(BulletinBoardActivity.class);
        mActivityContext.startActivity(intent);
    }

    /**
     * Generates the intent needed by the client code to launch this activity.
     */
    private Intent getLaunchIntent(Class activityClass) {
        return new Intent(mActivityContext, activityClass);
    }
}
