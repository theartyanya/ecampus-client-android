package com.kpi.campus;

/**
 * This class stores service constants of application.
 * <p>
 * Created by Administrator on 16.03.2016.
 */
public class Config {

    public static final String LOG = "kpi.campus";

    /**
     * Keys for intent
     */
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_BULLETIN = "key_bulletin";
    public static final String KEY_TITLE = "key_title";

    /**
     * Keys for SharedPreferences
     * Name of our shared preferences
     */
    public static final String SHARED_PREF_NAME = "campusapp";

    /**
     * Used to store the token of current logged in user
     */
    public static final String TOKEN_SHARED_PREF = "token";

    /**
     * Stores the boolean in SharedPreferences to track user is logged in or not
     */
    public static final String IS_LOGGED_SHARED_PREF = "islogged";

    public static final String USER_NAME = "user_name";

    public static final String USER_POSITION = "user_position";

    public static final String USER_SUBDIVISION = "user_subdivision";

    public static final String USER_IS_BB_MODERATOR = "user_is_moderator";
}
