package ua.kpi.campus.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by doroshartyom on 14.01.2015.
 */
public class PrefUtils {
    
    private static final String TAG = "PrefUtils";
    
    public static final String PREF_LOGIN_DONE = "pref_welcome_done";

    public static final String PREF_TOS_ACCEPTED = "pref_tos_accepted";
    
    public static final String PREF_SCHEDULE_UPLOADED = "pref_schedule_uploaded";

    public static final String PREF_GROUP_NAME = "pref_group_name";

    public static final String PREF_LOGIN = "pref_login";

    public static final String PREF_PASSWORD = "pref_password";

    public static final String PREF_AUTH_KEY = "pref_auth_key";
    
    public static final String PREF_AUTH_PASSED = "pref_auth_passed";
    
    public static final String PREF_GUEST_MODE = "pref_guest_mode";

    public static final String PREF_STUDY_GROUP_NAME = "pref_study_group_name";

    public static final String PREF_STUDY_FULLNAME = "pref_study_fullname";
    
    public static boolean isAuthPassed(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_AUTH_PASSED, false);
    }

    public static void markAuthPassed(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_AUTH_PASSED, true).commit();
    }
    
    public static String getPrefGroupName(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_GROUP_NAME, "");       
    }
    
    public static void setPrefGroupName(final Context context, String s) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_GROUP_NAME, s);
    }

    public static boolean isTosAccepted(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_TOS_ACCEPTED, false);
    }
    
    public static void markTosAccepted(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_TOS_ACCEPTED, true).commit();
    }
    
    public static boolean isScheduleUploaded(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_SCHEDULE_UPLOADED, false);
    }
    
    public static void markScheduleUploaded(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_SCHEDULE_UPLOADED, true).commit();
    }

    public static void unMarkScheduleUploaded(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_SCHEDULE_UPLOADED, false).commit();
    }

    public static void putLoginAndPassword(final Context context, String login, String password){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_LOGIN, login).putString(PREF_PASSWORD, password).commit();
    }

    public static String getLogin(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_LOGIN,"");
    }

    public static String getPassword(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_PASSWORD,"");
    }

    public static void putAuthKey(final Context context, String authKey){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_AUTH_KEY, authKey).commit();
    }

    public static String getAuthKey(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_AUTH_KEY,"");
    }

    public static String getPrefStudyGroupName(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_STUDY_GROUP_NAME,"");
    }

    public static void putPrefStudyGroupName(final Context context, String groupName){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_STUDY_GROUP_NAME, groupName).commit();
    }

    public static String getPrefStudyFullname(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_STUDY_FULLNAME,"Anonymous");
    }

    public static void putPrefStudyFullname(final Context context, String fullname){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_STUDY_FULLNAME, fullname).commit();
    }
    
    public static boolean isLoginDone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_LOGIN_DONE, false);
    }

    public static void markLoginDone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_LOGIN_DONE, true).commit();
    }

    public static void markLoginUndone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_LOGIN_DONE, false).commit();
    }
}
