package ua.kpi.campus.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import ua.kpi.campus.api.HTTP;


/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/7/13
 */
public class HTTPStringLoader extends AsyncTaskLoader<String> {
    private final String URL;
    private final String LOG_TAG = "HTTPStringLoader";

    public HTTPStringLoader(Context context, String URL) {
        super(context);
        this.URL = URL;
    }

    @Override
    public String loadInBackground() {
        Log.d(LOG_TAG, hashCode() + " loadInBackground start");
        return HTTP.getSting(URL);
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

