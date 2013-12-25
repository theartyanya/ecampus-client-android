package ua.kpi.campus.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import ua.kpi.campus.Mock;
import ua.kpi.campus.R;
import ua.kpi.campus.Session;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.api.jsonparsers.JSONAuthorizationParser;
import ua.kpi.campus.api.jsonparsers.JSONBulletinBoardParser;
import ua.kpi.campus.api.jsonparsers.JSONUserDataParser;
import ua.kpi.campus.api.jsonparsers.JsonObject;
import ua.kpi.campus.api.jsonparsers.user.UserData;
import ua.kpi.campus.loaders.HttpResponse;
import ua.kpi.campus.loaders.asynctask.AsyncTaskManager;
import ua.kpi.campus.loaders.asynctask.HttpLoadTask;
import ua.kpi.campus.loaders.asynctask.OnTaskCompleteListener;
import ua.kpi.campus.model.BulletinBoardSubject;
import ua.kpi.campus.model.CurrentUser;
import ua.kpi.campus.model.dbhelper.BulletinBoardBase;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;

import java.util.ArrayList;

public class LoginActivity extends FragmentActivity implements OnTaskCompleteListener {
    public final static String TAG = MainActivity.TAG;
    private final static int AUTH_LOADER_ID = 1;
    private final static int CURRENT_USER_LOADER_ID = 4;
    private EditText mLogin;
    private EditText mPassword;
    private AsyncTaskManager mAsyncTaskManager;
    private String sessionId;
    private OnClickListener loginButtonListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Log.d(TAG, hashCode() + " click!");
            if (mLogin.getText().length() == 0
                    || mPassword.getText().length() == 0) {
                showToastLong(getResources().getString(R.string.login_activity_fill_warning));
            } else {
                final String login = mLogin.getText().toString();
                final String password = mPassword.getText().toString();
                String url = CampusApiURL.getAuth(login, password);
                Log.d(TAG, hashCode() + " loading " + url);
                mAsyncTaskManager.setupTask(new HttpLoadTask(getResources(), url, R.string.login_activity_auth_work, AUTH_LOADER_ID));
            }
        }
    };

    //TODO test button
    private OnClickListener testButtonListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Log.d(this.getClass().getName(), hashCode() + " test click!");
            if (mLogin.getText().length() == 0
                    || mPassword.getText().length() == 0) {
                showToastLong(getResources().getString(R.string.login_activity_fill_warning));
            } else {
                final String login = mLogin.getText().toString();
                final String password = mPassword.getText().toString();
                //String Url = CampusApiURL.getAuth(login, password);
                //mAsyncTaskManager.setupTask(new HttpLoadTask(getResources(), Url, R.string.login_activity_auth_work, AUTH_LOADER_ID));
                //startSessionIdLoader(Url);
                try (DatabaseHelper db = new DatabaseHelper(getApplicationContext())) {

                    int accountID = parseUser(Mock.getUSER_EMPLOYEE()).getData().getUserAccountID();
                    CurrentUser user = new CurrentUser(accountID, "4344", login, password);
                    db.onUserChanged(user);
                    BulletinBoardBase bulletinBoardBase = BulletinBoardBase.getInstance();
                    bulletinBoardBase.addAllBulletins(parseBulletinBoard(Mock.getBulletinBoardActual()));
                }
                Session.setCurrentUser(parseUser(Mock.getUSER_EMPLOYEE()).getData());
                //Intent intent = new Intent(LoginActivity.this, MessageActivity.class);
                //intent.putExtra(MessagesViewFragment.EXTRA_GROUP_ID, 1);
                //Log.d(MainActivity.TAG, hashCode() + " starting new activity... " + MessageActivity.class.getName());
                //startActivity(intent);
                startMainActivity();
            }
        }
    };

    private ArrayList<BulletinBoardSubject> parseBulletinBoard(String JsonConversation) {
        try {
            return JSONBulletinBoardParser.parse(JsonConversation).getData();
        } catch (JSONException e) {
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        return new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = (EditText) findViewById(R.id.firstNumberEdit);
        mPassword = (EditText) findViewById(R.id.secondNumberEdit);
        Button sumButton = (Button) findViewById(R.id.sumButton);
        sumButton.setOnClickListener(loginButtonListener);
        Button testButton = (Button) findViewById(R.id.testButton);
        testButton.setOnClickListener(testButtonListener);

        mAsyncTaskManager = new AsyncTaskManager(this, this);
        mAsyncTaskManager.handleRetainedTask(getLastCustomNonConfigurationInstance());
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        // Delegate task retain to manager
        return mAsyncTaskManager.retainTask();
    }

    @Override
    public void onTaskComplete(HttpLoadTask task) {
        int currentLoaderId = task.getId();
        Log.d(TAG, hashCode() + " load finished, loader " + currentLoaderId);

        if (task.isCancelled()) {
            // Report about cancel
            Toast.makeText(this, R.string.task_cancelled, Toast.LENGTH_LONG)
                    .show();
        } else {
            // Get result
            HttpResponse httpResponse = null;
            try {
                httpResponse = task.get();
            } catch (Exception e) {
                Log.e(TAG, hashCode() + " task problem", e);
            }

            switch (currentLoaderId) {
                case AUTH_LOADER_ID:
                    checkAuth(httpResponse);
                    break;
                case CURRENT_USER_LOADER_ID:
                    updateUser(httpResponse);
                    break;
            }
        }
    }

    private void updateUser(HttpResponse httpResponse) {
        String userDataStr = httpResponse.getEntity();
        if (httpResponse.getStatusCode() == HttpStatus.SC_OK) {
            //TODO delete Session class
            Session.setCurrentUser(parseUser(userDataStr).getData());
            try (DatabaseHelper db = new DatabaseHelper(getApplicationContext())) {
                final String login = mLogin.getText().toString();
                final String password = mPassword.getText().toString();
                int accountID = parseUser(userDataStr).getData().getUserAccountID();
                CurrentUser user = new CurrentUser(accountID, sessionId, login, password);
                if (user.equals(db.getCurrentUser())) {
                    db.updateCurrentUser(user);
                } else {
                    db.onUserChanged(user);
                }
            }
            startMainActivity();
        } else {
            showToastLong(getResources().getString(R.string.access_denied));
        }
    }

    private void checkAuth(HttpResponse httpResponse) {
        final int statusCode = httpResponse.getStatusCode();
        final String response = httpResponse.getEntity();
        if (statusCode == HttpStatus.SC_OK) {
            try {
                // Create new manager and set this activity as context and listener
                mAsyncTaskManager = new AsyncTaskManager(this, this);
                // Handle task that can be retained before
                mAsyncTaskManager.handleRetainedTask(getLastCustomNonConfigurationInstance());
                Session.setSessionId(JSONAuthorizationParser.parse(response).getData());
                sessionId = JSONAuthorizationParser.parse(response).getData();
                String Url = CampusApiURL.getCurrentUser(Session.getSessionId());
                mAsyncTaskManager.setupTask(new HttpLoadTask(getResources(), Url, R.string.login_activity_getuser_work, CURRENT_USER_LOADER_ID));
            } catch (JSONException e) {
                showToastLong(getResources().getString(R.string.login_activity_json_error));
                Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
            }
        } else {
            showToastLong(getResources().getString(R.string.login_activity_auth_fail));
        }
    }

    private JsonObject<UserData> parseUser(String userDataStr) {
        try {
            return JSONUserDataParser.parse(userDataStr);
        } catch (JSONException e) {
            showToastLong(getResources().getString(R.string.login_activity_json_error));
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        //it`s ok because of checking for null further
        return null;
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        Log.d(TAG, hashCode() + " starting new activity... " + MainActivity.class.getName());
        startActivity(intent);
    }

    private void showToastLong(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        Log.d(TAG, hashCode() + " ToastLong:" + text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
