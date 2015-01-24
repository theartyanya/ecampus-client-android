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

    public static final String PREF_LOGIN = "pref_login";

    public static final String PREF_PASSWORD = "pref_password";

    public static final String PREF_AUTH_KEY = "pref_auth_key";
    
    public static final String PREF_AUTH_PASSED = "pref_auth_passed";
    
    public static final String PREF_GUEST_MODE = "pref_guest_mode";

    public static final String PREF_STUDY_GROUP_NAME = "pref_study_group_name";

    public static final String PREF_STUDY_FULLNAME = "pref_study_fullname";

    public static final String PREF_STUDY_CREDO = "pref_study_credo";

    public static final String PREF_STUDY_CONTACTS_JSON = "pref_study_contacts_json";

    public static final String PREF_STUDY_PERSONALITIES_SUBDIVISION_NAME = "pref_study_personalities_subdivision_name";

    public static final String PREF_STUDY_PERSONALITIES_SUBDIVISION_ID = "pref_study_personalities_subdivision_id";

    public static final String PREF_STUDY_EMPLOYEES_JSON = "pref_study_employees_json";

    public static final String PREF_STUDY_PHOTO_URL = "pref_study_photo_url";

    public static final String PREF_IS_STUDENT = "pref_is_student";

    public static final String PREF_FACULTY_NAME = "pref_faculty_name";

    public static final String PREF_MY_ID="pref_my_id";

    public static void putPrefMyId(final Context context, int id){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_MY_ID, id).commit();
    }

    public static int getMyId(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_MY_ID, -1);
    }


    public static void putPrefFacultyName(final Context context, String facultyName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_FACULTY_NAME,facultyName).commit();
    }

    public static String getPrefFacultyName(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_FACULTY_NAME, "");
    }

    public static boolean isStudent(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_IS_STUDENT, true);
    }

    public static void setIsStudent(final Context context, boolean isStudent){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_IS_STUDENT, isStudent).commit();
    }

    public static String getPrefStudyPhotoUrl(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_STUDY_PHOTO_URL, "");
    }

    public static String getPrefStudyEmployeesJson(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_STUDY_EMPLOYEES_JSON, "");
    }

    public static int getPrefStudyPersonalitiesSubdivisionId(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_STUDY_PERSONALITIES_SUBDIVISION_ID, 0);
    }

    public static String getPrefStudyPersonalitiesSubdivisionName(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_STUDY_PERSONALITIES_SUBDIVISION_NAME, "");
    }

    public static String getPrefStudyContactsJson(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_STUDY_CONTACTS_JSON, "");
    }

    public static String getPrefStudyCredo(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_STUDY_CREDO, "");
    }

    public static void putStudyPhotoURL(final Context context, String url){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_STUDY_PHOTO_URL, url).commit();
    }

    public static void putStudyEmployeesJSON(final Context context, String json){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_STUDY_EMPLOYEES_JSON, json).commit();
    }

    public static void putStudyPersonalitiesSubdivisionNameAndId(final Context context, String name, int id){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_STUDY_PERSONALITIES_SUBDIVISION_NAME, name).putInt(PREF_STUDY_PERSONALITIES_SUBDIVISION_ID, 0).commit();
    }

    public static void putStudyContactsJSON(final Context context, String json){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_STUDY_CONTACTS_JSON, json).commit();
    }

    public static void putStudyCredo(final Context context, String credo){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_STUDY_CREDO, credo).commit();
    }

    public static boolean isAuthPassed(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_AUTH_PASSED, false);
    }

    public static void markAuthPassed(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_AUTH_PASSED, true).commit();
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
