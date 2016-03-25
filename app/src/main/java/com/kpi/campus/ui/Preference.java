package com.kpi.campus.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.kpi.campus.Config;
import com.kpi.campus.di.ActivityContext;
import com.kpi.campus.model.pojo.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Implement queries to SharedPrefernces.
 * <p>
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
     *
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

    public void saveUserInfo(User user) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(Config.USER_NAME, user.name);
        Gson gson = new Gson();
        editor.putString(Config.USER_POSITION, gson.toJson(user.position));
        editor.putString(Config.USER_SUBDIVISION, user.subdivision);
        editor.putBoolean(Config.USER_IS_BB_MODERATOR, user.isBulletinBoardModerator);
        editor.commit();
    }

    public String getUserName() {
        return mSharedPrefs.getString(Config.USER_NAME, "");
    }

    public List<String> getUserPositions() {
        String jsonPositions = mSharedPrefs.getString(Config.USER_POSITION, "");
        Gson gson = new Gson();
        String[] positions = gson.fromJson(jsonPositions, String[].class);
        return (positions != null) ? new ArrayList(Arrays.asList(positions)) : new ArrayList<>();
    }

    public String getUserSubdivision() {
        return mSharedPrefs.getString(Config.USER_SUBDIVISION, "");
    }

    public boolean getIsUserBbModerator() {
        return mSharedPrefs.getBoolean(Config.USER_IS_BB_MODERATOR, false);
    }

    public String getToken() {
        return mSharedPrefs.getString(Config.TOKEN_SHARED_PREF, "");
    }
}
