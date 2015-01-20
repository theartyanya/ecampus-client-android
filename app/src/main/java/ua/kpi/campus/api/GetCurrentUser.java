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
import org.json.JSONObject;

import java.net.UnknownHostException;

import ua.kpi.campus.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.util.PrefUtils;

/**
 * Created by dmitry on 18.01.15.
 */
public class GetCurrentUser extends AsyncTask<Context, Integer, Integer> {

    private Context mContext;
    public GetCurrentUser(Context context){
        mContext=context;
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
                    String groupName =  jsonResponce.getJSONObject("Data").getJSONArray("Personalities").getJSONObject(0).getString("StudyGroupName");
                    PrefUtils.putPrefStudyGroupName(mContext, groupName);
                    PrefUtils.putPrefStudyFullname(mContext, jsonResponce.getJSONObject("Data").getString("FullName"));
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
            if(!PrefUtils.isScheduleUploaded(mContext)){
                //SyncSchedule sync = SyncSchedule.getSyncSchedule(PrefUtils.getPrefStudyGroupName(mContext), mContext);
                //SyncSchedule.Connect connect = new SyncSchedule.Connect();
                //connect.execute(mContext);

            }


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

}
