package ua.kpi.campus.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;


/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/7/13
 */
public class HTTPStringLoader extends AsyncTaskLoader<String> {
    public final static String URL_STRING = "URL";
    private final String URL_ADDRESS;
    private final String LOG_TAG = "HTTPStringLoader";

    public HTTPStringLoader(Context context, String URL_ADDRESS) {
        super(context);
        this.URL_ADDRESS = URL_ADDRESS;
    }

    @Override
    public String loadInBackground() {
        Log.d(LOG_TAG, hashCode() + " loadInBackground start");
        return HTTP.getSting(URL_ADDRESS);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(LOG_TAG, hashCode() + " onStartLoading");
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(LOG_TAG, hashCode() + " onStopLoading");
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
        Log.d(LOG_TAG, hashCode() + " onAbandon");
    }

    @Override
    protected void onReset() {
        super.onReset();
        Log.d(LOG_TAG, hashCode() + " onReset");
    }

}

