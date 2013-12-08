package ua.kpi.campus.loaders;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

class Http {
    static HttpResponse getString(String URL) {
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
}