package com.kpi.campus.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.kpi.campus.Config;
import com.kpi.campus.di.ActivityContext;

import javax.inject.Inject;

/**
 * Implement queries to SharedPrefernces.
 *
 * Created by Administrator on 21.03.2016.
 */
public class Preference {

    private Activity mActivityContext;
    private SharedPreferences mSharedPrefs;

    @Inject
    public Preference(@ActivityContext Context context) {
        mActivityContext = (Activity) context;
        mSharedPrefs = mActivityContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }


    /**
     * Save login values to SharedPreferences.
     */
    public void saveLoginInfo(String tokenValue) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();

        //Adding values to editor
        editor.putBoolean(Config.IS_LOGGED_SHARED_PREF, true);
        editor.putString(Config.TOKEN_SHARED_PREF, tokenValue);

        //Saving values to editor
        editor.commit();
    }

    /**
     * Get value is user logged.
     * @return is logged
     */
    public boolean getIsLogged() {
        return mSharedPrefs.getBoolean(Config.IS_LOGGED_SHARED_PREF, false);
    }

    /**
     * Save logout values to SharedPreferences.
     */
    public void saveLogoutInfo() {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putBoolean(Config.IS_LOGGED_SHARED_PREF, false);
        editor.putString(Config.TOKEN_SHARED_PREF, "");
        editor.commit();
    }
}
