package ua.kpi.campus.loaders;

import android.graphics.BitmapFactory;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HTTP {
    public static HttpResponse getString(String URL) {
        Log.d(HttpStringLoader.class.getName(), " httpStartedLoad");
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(URL);
            org.apache.http.HttpResponse response = client.execute(request);
            HttpEntity resEntity = response.getEntity();
            Log.d(HttpStringLoader.class.getName(), " httpFinishedLoad");
            return new HttpResponse(response.getStatusLine().getStatusCode(),EntityUtils.toString(resEntity));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return new HttpResponse(HttpStatus.SC_NOT_FOUND,null);
    }

    static android.graphics.Bitmap getBitmap(String url) {
        android.graphics.Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);


        } catch (Exception e) {
            Log.e(HttpStringLoader.LOG_TAG, "Error getting bitmap", e);
        }
        return bm;
    }
}