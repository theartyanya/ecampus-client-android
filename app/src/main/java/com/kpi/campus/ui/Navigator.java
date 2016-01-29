package com.kpi.campus.ui;

import android.app.Activity;
import android.content.Context;

import com.kpi.campus.di.ActivityContext;

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
}
