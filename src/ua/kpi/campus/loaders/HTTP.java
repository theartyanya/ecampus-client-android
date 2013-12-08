package ua.kpi.campus.loaders;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

class HTTP {
    public static final String ERROR_STRING = "error";

    static String getSting(String URL) {
        String str = "error";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(URL);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                str = EntityUtils.toString(resEntity);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return str;
    }
}