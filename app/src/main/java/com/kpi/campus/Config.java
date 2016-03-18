package com.kpi.campus;

/**
 * This class stores service constants of application.
 *
 * Created by Administrator on 16.03.2016.
 */
public class Config {

    public static final String LOG = "kpi.campus";

    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";

    // Keys for SharedPreferences
    // Name of our shared preferences
    public static final String SHARED_PREF_NAME = "campusnapp";

    // Used to store the token of current logged in user
    public static final String TOKEN_SHARED_PREF = "token";

    // Stores the boolean in SharedPreferences to track user is logged in or not
    public static final String IS_LOGGED_SHARED_PREF = "islogged";
}
