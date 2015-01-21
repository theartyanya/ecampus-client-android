package ua.kpi.campus.api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ua.kpi.campus.MainActivity;
import ua.kpi.campus.model.ScheduleItemTeacher;
import ua.kpi.campus.provider.ScheduleProvider;
import ua.kpi.campus.util.PrefUtils;

/**
 * Created by dmitry on 20.01.15.
 */
public class SyncScheduleTeacher {
    private static final String LOG_TAG="SyncScheduleTeacher";
    private static int teacherId;
    private static Context mContext;
    private static ScheduleProvider provider;
    private static ArrayList<ScheduleItemTeacher> itemsArrayList = new ArrayList<>();


    public SyncScheduleTeacher(Context context, int teacherId){
        SyncScheduleTeacher.mContext=context;
        SyncScheduleTeacher.teacherId=teacherId;
    }
    public interface CallBacks {
        Context getContext();
        void taskStarted(boolean started);
        void taskCompleted(boolean completed);
    }
    public static void getSchedule() throws Exception{
        HttpClient httpclient = new DefaultHttpClient();
        String URL="http://api.rozklad.org.ua/v2/teachers/"+teacherId+"/lessons";

        try{
            HttpGet httpget = new HttpGet(URL);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity httpEntity =response.getEntity();
            String answer = EntityUtils.toString(httpEntity, "UTF-8");
            JSONObject jsonResponse = new JSONObject(answer);
            JSONArray data = jsonResponse.getJSONArray("data");

            for(int i = 0; i<data.length();i++){
                Log.d(LOG_TAG, "Adding to schedule shit #" + i);
                JSONObject lesson = data.getJSONObject(i);
                ScheduleItemTeacher lessonItem = new ScheduleItemTeacher();

                lessonItem.setLessonId(Integer.parseInt(lesson.getString("lesson_id")));
                lessonItem.setGroupId(Integer.parseInt(lesson.getString("group_id")));
                
                //getting group name
                HttpGet httpget2 = new HttpGet("http://api.rozklad.org.ua/v2/groups/"+lessonItem.getGroupId());
                HttpResponse response2 = httpclient.execute(httpget2);
                HttpEntity httpEntity2 =response2.getEntity();
                String answer2 = EntityUtils.toString(httpEntity2, "UTF-8");
                JSONObject jsonResponse2 = new JSONObject(answer2);
                JSONObject data2 = jsonResponse2.getJSONObject("data");

                lessonItem.setGroupName(data2.getString("group_full_name"));
                lessonItem.setDayNumber(Integer.parseInt(lesson.getString("day_number")));
                lessonItem.setLessonName(lesson.getString("lesson_name"));
                lessonItem.setLessonNumber(Integer.parseInt(lesson.getString("lesson_number")));
                lessonItem.setLessonRoom(lesson.getString("lesson_room"));
                lessonItem.setTeacherName(lesson.getString("teacher_name"));
                lessonItem.setLessonWeek(Integer.parseInt(lesson.getString("lesson_week")));
                lessonItem.setTimeStart(lesson.getString("time_start"));
                lessonItem.setTimeEnd(lesson.getString("time_end"));

                itemsArrayList.add(lessonItem);

            }
            provider = new ScheduleProvider(SyncScheduleTeacher.mContext);
            provider.clearTeacherSchedule();

            for(ScheduleItemTeacher item : itemsArrayList){
                provider.addToScheduleDatabase(item);
            }
        }catch(Exception e){
            throw e;
        }


    }

    //Inner class to send requests not from main thread
    public static class Connect extends AsyncTask<Void, Void, Void> {
        Context asyncContext=SyncScheduleTeacher.mContext;

        CallBacks callBacks;
        Boolean useContext=false;

        public Connect(CallBacks mCallBacks) {
            this.callBacks = mCallBacks;

        }

        public Connect(Context asyncContext,Boolean useContext){
            this.asyncContext=asyncContext;
            this.useContext=useContext;
        }
        @Override
        public Void doInBackground(Void... arg) {
            try {
                Log.d(LOG_TAG, "Trying to get items");
                getSchedule();
                return null;

            }catch (Exception e) {
                PrefUtils.unMarkScheduleUploaded(asyncContext);
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v){
            if(!useContext)
                callBacks.taskCompleted(true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callBacks.taskStarted(true);
        }
    }

}
