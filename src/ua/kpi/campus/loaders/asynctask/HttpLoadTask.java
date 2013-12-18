package ua.kpi.campus.loaders.asynctask;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import ua.kpi.campus.Activity.LoginActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.loaders.HTTP;
import ua.kpi.campus.loaders.HttpResponse;

public class HttpLoadTask extends AsyncTask<Void, String, HttpResponse> {

    protected final Resources mResources;
    private HttpResponse mResult;
    private String mProgressMessage;
    private IProgressTracker mProgressTracker;
    private String url;
    private int mWorkString;
    private int id;

    /* UI Thread */
    public HttpLoadTask(Resources resources, String url, int workString) {
        this.url = url;
        // Keep reference to resources
        mResources = resources;
        // Initialise initial pre-execute message
        mProgressMessage = resources.getString(R.string.task_starting);
        this.mWorkString = workString;
        id = 0;
    }

    /* UI Thread */
    public HttpLoadTask(Resources resources, String url, int workString, int id) {
        this.url = url;
        // Keep reference to resources
        mResources = resources;
        // Initialise initial pre-execute message
        mProgressMessage = resources.getString(R.string.task_starting);
        this.mWorkString = workString;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /* UI Thread */
    public void setProgressTracker(IProgressTracker progressTracker) {
        // Attach to progress tracker
        mProgressTracker = progressTracker;
        // Initialise progress tracker with current task state
        if (mProgressTracker != null) {
            mProgressTracker.onProgress(mProgressMessage);
            if (mResult != null) {
                mProgressTracker.onComplete();
            }
        }
    }

    /* UI Thread */
    @Override
    protected void onCancelled() {
        // Detach from progress tracker
        mProgressTracker = null;
    }

    /* UI Thread */
    @Override
    protected void onProgressUpdate(String... values) {
        // Update progress message
        mProgressMessage = values[0];
        // And send it to progress tracker
        if (mProgressTracker != null) {
            mProgressTracker.onProgress(mProgressMessage);
        }
    }

    /* UI Thread */
    @Override
    protected void onPostExecute(HttpResponse result) {
        // Update result
        mResult = result;
        // And send it to progress tracker
        if (mProgressTracker != null) {
            mProgressTracker.onComplete();
        }
        // Detach from progress tracker
        mProgressTracker = null;
    }

    private HttpLoadTask getInstance() {
        return this;
    }

    /* Separate Thread */
    @Override
    protected HttpResponse doInBackground(Void... arg0) {
        // Working in separate thread

/*        new Thread() {
            @Override
            public synchronized void start() {
                int i = 0;
                while (isCancelled()) {
                    i++;
                    try {
                        // This call causes onProgressUpdate call on UI thread
                        publishProgress(mResources.getString(R.string.task_working, i));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };*/

        publishProgress(mResources.getString(mWorkString));
        Log.d(LoginActivity.LOG_TAG, hashCode() + " loadInBackground start");
        return HTTP.getString(url);
    }
}