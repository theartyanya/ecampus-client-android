package ua.kpi.campus.Activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
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
import ua.kpi.campus.api.jsonparsers.JSONUserDataParser;
import ua.kpi.campus.api.jsonparsers.JsonObject;
import ua.kpi.campus.api.jsonparsers.user.UserData;
import ua.kpi.campus.loaders.HttpResponse;
import ua.kpi.campus.loaders.HttpStringLoader;

public class LoginActivity extends Activity implements LoaderManager.LoaderCallbacks<HttpResponse> {
    private final static int AUTH_LOADER_ID = 1;
    private final static int CURRENT_USER_LOADER_ID = 4;
    private EditText firstNumber;
    private EditText secondNumber;
    private Button sumButton;
    private LoaderManager.LoaderCallbacks<HttpResponse> mCallbacks;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firstNumber = (EditText) findViewById(R.id.firstNumberEdit);
        secondNumber = (EditText) findViewById(R.id.secondNumberEdit);
        sumButton = (Button) findViewById(R.id.sumButton);
        sumButton.setOnClickListener(sumButtonListener);

        mCallbacks = this;
        loaderManager = getLoaderManager();
    }

    private OnClickListener sumButtonListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Log.d(this.getClass().getName(), hashCode() + " click!");
            if (firstNumber.getText().length() == 0
                    || secondNumber.getText().length() == 0) {
                showToastLong(getResources().getString(R.string.login_activity_fill_warning));
            } else {
                final String login = firstNumber.getText().toString();
                final String password = secondNumber.getText().toString();
                String Url = CampusApiURL.getAuth(login, password);
                //TODO changed to load from mock
                //startSessionIdLoader(Url);
                Session.setCurrentUser(parseUser(Mock.getUSER_EMPLOYEE()).getData());
                startMainActivity();
            }
        }
    };

    private void checkAuth(HttpResponse httpResponse) {
        final int statusCode = httpResponse.getStatusCode();
        final String response = httpResponse.getEntity();
        if (statusCode == HttpStatus.SC_OK) {
            try {
                //showToastLong(response);
                Session.setSessionId(JSONAuthorizationParser.parse(response).getData());
                startCurrentUserLoader(Session.getSessionId());
            } catch (JSONException e) {
                showToastLong(getResources().getString(R.string.login_activity_json_error));
                Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
            }
        } else {
            showToastLong(getResources().getString(R.string.login_activity_auth_fail));
        }
    }

    private void startSessionIdLoader(String url) {
        Bundle authData = new Bundle();
        authData.putString(HttpStringLoader.URL_STRING, url);
        loaderManager.restartLoader(AUTH_LOADER_ID, authData, mCallbacks).onContentChanged();
    }

    private void startCurrentUserLoader(String sessionId) {
        startHttpLoader(CURRENT_USER_LOADER_ID, CampusApiURL.getCurrentUser(sessionId));
    }

    private void startHttpLoader(int id, String url) {
        Bundle permissionsData = new Bundle();
        permissionsData.putString(HttpStringLoader.URL_STRING, url);
        loaderManager.restartLoader(id, permissionsData, mCallbacks).onContentChanged();
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
        Log.d(this.getClass().getName(), hashCode() + " starting new activity... " + MainActivity.class.getName());
        startActivity(intent);
    }

    private void showToastLong(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        Log.d(LoginActivity.class.getName(), hashCode() + " ToastLong:" + text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public Loader<HttpResponse> onCreateLoader(int i, Bundle bundle) {
        Log.d(this.getClass().getName(), hashCode() + " load started " + i);
        return new HttpStringLoader(LoginActivity.this, bundle.getString(HttpStringLoader.URL_STRING));
    }

    @Override
    public void onLoadFinished(Loader<HttpResponse> httpResponseLoader, HttpResponse httpResponse) {
        int currentLoaderId = httpResponseLoader.getId();
        final String userDataStr = httpResponse.getEntity();
        Log.d(this.getClass().getName(), hashCode() + " load finished (loader)" + currentLoaderId
                + "\n---------\n" + httpResponse);
        switch (currentLoaderId) {
            case AUTH_LOADER_ID:
                checkAuth(httpResponse);
                break;
            case CURRENT_USER_LOADER_ID:
                if (httpResponse.getStatusCode() == HttpStatus.SC_OK) {
                    Session.setCurrentUser(parseUser(userDataStr).getData());
                    startMainActivity();
                } else {
                    showToastLong(getResources().getString(R.string.login_activity_access_denied));
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<HttpResponse> httpResponseLoader) {

    }
}
