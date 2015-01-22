package ua.kpi.campus.api;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import ua.kpi.campus.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.model.ScheduleItem;
import ua.kpi.campus.model.TeacherItem;
import ua.kpi.campus.provider.ScheduleProvider;
import ua.kpi.campus.util.PrefUtils;

/**
 * Created by doroshartyom on 08.01.2015.
 */
public class SyncSchedule {

    public static final String BASE_URL = "http://api.rozklad.org.ua/v1/";
    private static final String LOG_TAG = "SyncSchedule";

    //Singleton
    private static SyncSchedule syncSchedule;

    private static String GROUP;
    private static Context context;
    private static ScheduleProvider provider;

    //Array of Subject objects to add them to database later
    private static ArrayList<ScheduleItem> itemsArrayList = new ArrayList<ScheduleItem>();
    private static ArrayList<TeacherItem> teacherList = new ArrayList<TeacherItem>();

    public interface CallBacks {
        Context getContext();
        void syncScheduleCompleted(boolean completed);
    }
    
    private SyncSchedule(String groupName, Context context) {
        SyncSchedule.GROUP= groupName;
        SyncSchedule.context = context;

    }

    public static SyncSchedule getSyncSchedule(String groupName, Context context) {

        if (syncSchedule != null)
            return syncSchedule;
        syncSchedule = new SyncSchedule(groupName, context);

        return syncSchedule;
    }

    /*This method sends request to http://api.... and gets JSON object
        then we add Subject objects to database
     */
    public static void getSchedule(Context context) throws Exception{
        String URL = "http://api.rozklad.org.ua/v1/groups/"+PrefUtils.getPrefStudyGroupName(context)+"/lessons";
        BufferedReader in = null;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        request.setURI(new URI(URL));
        HttpResponse response = client.execute(request);

        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));


        StringBuffer buffer = new StringBuffer("");
        String line = "";

        String NL = System.getProperty("line.separator");

        Log.d(LOG_TAG, "Reading lines...");
        while ((line = in.readLine()) != null){
            buffer.append(line + NL);
        }

        String page = buffer.toString();
        JSONArray array = new JSONArray(page);
        for(int i = 0; i < array.length(); i++) {
            Log.d(LOG_TAG, "Iteration number " + i + "...");
            ContentValues cv = new ContentValues();
            ScheduleItem sb = new ScheduleItem();
            TeacherItem teacherItem = new TeacherItem();
            JSONObject obj = array.getJSONObject(i);

            ScheduleItem current = new ScheduleItem();

            int teacher_id = -1;
            
            JSONArray teachers = obj.getJSONArray("teachers");
            if (teachers.length()!=0) {
                JSONObject teacher = teachers.getJSONObject(0);
                teacher_id = teacher.getInt("teacher_id");
                String teacher_name = teacher.getString("teacher_name");

                teacherItem.setTeacherId(teacher_id)
                        .setTeacherSubject(obj.getString("lesson_name"))
                        .setTeacherName(teacher_name);

                
                if (!(teacherList.contains(teacherItem))) {
                    Log.d(LOG_TAG, teacherItem.toString() + "is added to teacherList");
                    teacherList.add(teacherItem);
                } else {
                    Log.d(LOG_TAG, teacherItem.toString() + "is already in teacherList");
                }
            }
            
            current.setLessonId(obj.getInt("lesson_id"))
                    .setDayNumber(obj.getInt("day_number"))
                    .setLessonNumber(obj.getInt("lesson_number"))
                    .setLessonName(obj.getString("lesson_name"))
                    .setLessonRoom(obj.getString("lesson_room"))
                    .setTeacherName(obj.getString("teacher_name"))
                    .setTeacherId(teacher_id)
                    .setLessonWeek(obj.getInt("lesson_week"))
                    .setTimeStart(obj.getString("time_start"))
                    .setTimeEnd(obj.getString("time_end"));


            itemsArrayList.add(current);
            
            
        }

        provider = new ScheduleProvider(context);
        Log.d(LOG_TAG, itemsArrayList.size() + "");
        provider.clear();
        
        int incre = 1;
        for(ScheduleItem i: itemsArrayList) {
            Log.d(LOG_TAG, "Adding lesson #"+ incre++);
            provider.addToScheduleDatabase(i);
        }
        
        Log.d(LOG_TAG, "Done for schedule");
        
        incre = 1;
        
        for (TeacherItem i: teacherList) {
            Log.d(LOG_TAG, "Adding teacher #" + incre++);
            provider.addToTeachersDatabase(i);
        }


    }

    //Method sends request to APi and gets what week is now
    public static int getWeek() throws Exception {

        BufferedReader in = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();

        request.setURI(new URI(BASE_URL + "weeks"));
        HttpResponse response = client.execute(request);
        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String weekString = in.readLine();
        in.close();

        return Integer.parseInt(weekString);
    }

    //Inner class to send requests not from main thread
    public static class Connect extends AsyncTask<Context, Void, Void> {
        Context asyncContext;
        
        CallBacks callBacks;
        
        public Connect(CallBacks mCallBacks) {
            this.callBacks = mCallBacks;
            
        }

        @Override
        public Void doInBackground(Context... arg) {
            try {
                asyncContext = arg[0];
                Log.d(LOG_TAG, "Trying to get items");
                getSchedule(arg[0]);
                return null;

            }catch (Exception e) {
                PrefUtils.unMarkScheduleUploaded(arg[0]);
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v){

            
            if(PrefUtils.getPrefStudyGroupName(asyncContext).isEmpty()){
                PrefUtils.markLoginUndone(asyncContext);
            }else if(PrefUtils.isTosAccepted(asyncContext)){
                PrefUtils.markLoginDone(asyncContext);
                PrefUtils.markScheduleUploaded(asyncContext);
                callBacks.syncScheduleCompleted(true);
            }

        }
    }

}
