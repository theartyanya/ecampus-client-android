package ua.kpi.campus.api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import ua.kpi.campus.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.util.PrefUtils;

/**
 * Created by dmitry on 18.01.15.
 */
public class GetCurrentUser extends AsyncTask<Context, Integer, Integer> {
    CallBacks callBacks;
    private Context mContext;
    public GetCurrentUser(Context context, CallBacks mCallbacks){
        mContext=context;
        this.callBacks=mCallbacks;
    }
    public static boolean completed=false;
    public interface CallBacks{
        Context getContext();
        void GetCurrentUserCompleted(boolean completed);
    }

    @Override
    protected Integer doInBackground(Context... params) {
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet("http://campus-api.azurewebsites.net/User/GetCurrentUser?sessionId="+PrefUtils.getAuthKey(mContext));
        try{

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity httpEntity =response.getEntity();
            String answer = EntityUtils.toString(httpEntity, "UTF-8");

            JSONObject jsonResponce = new JSONObject(answer);
            Log.d("JSONanswer", jsonResponce.toString());
            switch (jsonResponce.getInt("StatusCode")){
                case 200:
                    JSONArray personalities = jsonResponce.getJSONObject("Data").getJSONArray("Personalities");
                    String groupName =  personalities.getJSONObject(0).getString("StudyGroupName");
                    PrefUtils.putPrefStudyGroupName(mContext, groupName);
                    PrefUtils.putPrefStudyFullname(mContext, jsonResponce.getJSONObject("Data").getString("FullName"));
                    if(!personalities.isNull(0)){
                        PrefUtils.putStudyPersonalitiesSubdivisionNameAndId(mContext, personalities.getJSONObject(0).getString("SubdivisionName"),personalities.getJSONObject(0).getInt("SubdivisionId"));
                    }

                    //TODO must be an easier way
                    //check is user student or teacher
                    if(jsonResponce.getJSONObject("Data").getJSONArray("Profiles").getJSONObject(0).getString("ProfileName").equalsIgnoreCase("Студент навчальної групи")){
                        PrefUtils.setIsStudent(mContext,true);
                    }else{
                        PrefUtils.setIsStudent(mContext,false);
                    }

                    JSONArray employees = jsonResponce.getJSONObject("Data").getJSONArray("Employees");
                    if(!employees.isNull(0)){
                        PrefUtils.putStudyEmployeesJSON(mContext, employees.toString());
                    }
                    JSONArray contacts = jsonResponce.getJSONObject("Data").getJSONArray("Contacts");
                    if(!contacts.isNull(0)){
                        PrefUtils.putStudyContactsJSON(mContext, contacts.toString());
                    }
                    PrefUtils.putStudyPhotoURL(mContext, jsonResponce.getJSONObject("Data").getString("Photo"));
                    Log.d("fullname",PrefUtils.getPrefStudyFullname(mContext)+" group "+PrefUtils.getPrefStudyGroupName(mContext));
                    return 200; //it seems everything is good
                case 403:
                    makeSnackBarInUI(403);
                    return 403; //access denied
                case 500:
                    return 500; //internal server problems
                default:
                    return 0; //unlisted code
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
            return -2; //connection problems
        }
        catch(UnknownHostException e){
            return -3;
        }
        catch(Exception e){
            Log.e("error",e.toString());
            e.printStackTrace();
            return -1; //exception raised
        }
    }
    @Override
    protected void onPostExecute(Integer result) {
        if(!PrefUtils.getAuthKey(mContext).isEmpty()){
            callBacks.GetCurrentUserCompleted(true);


        }

    }



    public void makeSnackBarInUI(final int i){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                switch(i) {
                    case 403:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text("Invalid sessionID"));
                        break;
                    case 500:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.server_is_down) + " (500)"));
                        break;
                    case -2:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.server_is_down) + " (-2)"));
                        break;
                    case -1:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.internal_error) + " (-1)"));
                        break;
                    case -3:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.no_internet)));
                        break;
                    default:
                        SnackbarManager.show(
                                Snackbar.with(mContext)
                                        .text(mContext.getString(R.string.internal_error) + " (d)"));
                        break;
                }
            }
        });
    }


    //PUBLIC API
    //static methods
    public static ArrayList<HashMap<String,String>> getContacts(Context context){
        ArrayList<HashMap<String,String>> returnList = new ArrayList<>();
        try{
            JSONArray contactsJSON = new JSONArray(PrefUtils.getPrefStudyContactsJson(context));

            for(int i = 0; i<contactsJSON.length();i++){
                JSONObject object = contactsJSON.getJSONObject(i);
                HashMap<String,String> map = new HashMap<>();
                map.put("UserContactId",object.getString("UserContactId"));
                map.put("UserAccountId",object.getString("UserAccountId"));
                map.put("ContactTypeName",object.getString("ContactTypeName"));
                map.put("UserContactValue",object.getString("UserContactValue"));
                map.put("VcActuality",object.getString("VcActuality"));
                map.put("VcChangeDate",object.getString("VcChangeDate"));
                map.put("IsVisible",object.getString("IsVisible"));
                map.put("ReceptionHours",object.getString("ReceptionHours"));
                returnList.add(0,map);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return  returnList;
    }

    public static ArrayList<HashMap<String,String>> getEmployees(Context context){
        ArrayList<HashMap<String,String>> returnList = new ArrayList<>();
        try{
            JSONArray employeesJSON = new JSONArray(PrefUtils.getPrefStudyEmployeesJson(context));

            for(int i = 0; i<employeesJSON.length();i++){
                JSONObject object = employeesJSON.getJSONObject(i);
                HashMap<String,String> map = new HashMap<>();
                map.put("SubdivisionId",object.getString("SubdivisionId"));
                map.put("SubdivisionName",object.getString("SubdivisionName"));
                map.put("Position",object.getString("Position"));
                map.put("AcademicDegree",object.getString("AcademicDegree"));
                map.put("AcademicStatus",object.getString("AcademicStatus"));
                returnList.add(0,map);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return  returnList;
    }

    //returns name of Students department
    public static ArrayList<String> getSubdivisionNames(Context context){
        ArrayList<String> returnList = new ArrayList<>();

        if(PrefUtils.isStudent(context)){
            returnList.add(PrefUtils.getPrefStudyPersonalitiesSubdivisionName(context));
        }else{
            ArrayList<HashMap<String,String>> employees = getEmployees(context);
            for(int i = 0; i<employees.size();i++){
                returnList.add(employees.get(i).get("SubdivisionName"));
            }
        }
        return returnList;
    }

    public static String getCredo(Context context){
        return PrefUtils.getPrefStudyCredo(context);
    }

    public static String getPhotoURL(Context context){
        return PrefUtils.getPrefStudyPhotoUrl(context);
    }
    //TODO THIS IS TEMPORARY SOLUTION, COULD NOT WORK PROPERLY
    public static String getFaculty(Context context){
        String subdivision = getSubdivisionNames(context).get(0);
        return subdivision.substring(subdivision.lastIndexOf(' '));
    }




}
