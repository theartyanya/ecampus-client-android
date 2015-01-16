package ua.kpi.campus.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by doroshartyom on 14.01.2015.
 */
public class PrefUtils {
    
    private static final String TAG = "PrefUtils";
    
    public static final String PREF_WELCOME_DONE = "pref_welcome_done";

    public static final String PREF_TOS_ACCEPTED = "pref_tos_accepted";
    
    public static final String PREF_SCHEDULE_UPLOADED = "pref_schedule_uploaded";

    public static final String PREF_GROUP_NAME = "pref_group_name";
    
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
        sp.edit().putBoolean(PREF_SCHEDULE_UPLOADED, true);
        
    }
}
