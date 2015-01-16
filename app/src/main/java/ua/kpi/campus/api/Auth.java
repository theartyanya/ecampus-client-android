package ua.kpi.campus.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import ua.kpi.campus.util.PrefUtils;

/**
 * Created by doroshartyom on 08.01.2015.
 */
public class Auth extends AsyncTask<Context, Integer, Integer> {

    private Context mContext;
    public Auth(Context context){
        mContext=context;
    }

    @Override
    protected Integer doInBackground(Context... params) {
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet("http://campus-api.azurewebsites.net/User/Auth?login="+PrefUtils.getLogin(mContext)+"&password="+PrefUtils.getPassword(mContext));
        try{

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity httpEntity =response.getEntity();
            String answer = EntityUtils.toString(httpEntity, "UTF-8");

            JSONObject jsonResponce = new JSONObject(answer);
            Log.d("JSONanswer",jsonResponce.toString());
            switch (jsonResponce.getInt("StatusCode")){
                case 200:
                    PrefUtils.putAuthKey(mContext, jsonResponce.getString("Data"));
                    return 200; //it seems everything is good
                case 403:
                    return 403; //access denied
                case 500:
                    Log.i("async","500");
                    return 500; //internal server problems
                default:
                    Log.i("async","0");
                    return 0; //unlisted code
            }
        }catch(Exception e){
            Log.e("error",e.toString());
            e.printStackTrace();
            return -1; //exception raised
        }
    }


}
