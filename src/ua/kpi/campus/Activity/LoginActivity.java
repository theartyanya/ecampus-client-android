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
import ua.kpi.campus.R;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.api.jsonparsers.*;
import ua.kpi.campus.loaders.HttpResponse;
import ua.kpi.campus.loaders.HttpStringLoader;

public class LoginActivity extends Activity implements LoaderManager.LoaderCallbacks<HttpResponse>{
	private EditText firstNumber;
	private EditText secondNumber;
	private Button sumButton;
    private final static int AUTH_LOADER_ID = 1;
    private final static int PERMISSIONS_LOADER_ID = 2;
    private final static int USER_DATA_LOADER_ID = 3;
    private final static int USER_LOADER_ID = 4;
    private LoaderManager.LoaderCallbacks<HttpResponse> mCallbacks;
    private LoaderManager loaderManager;
    private Permissions permissions;
    private UserData userData;
    private UserData currentUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        firstNumber = (EditText)findViewById(R.id.firstNumberEdit);
        secondNumber = (EditText)findViewById(R.id.secondNumberEdit);
        sumButton = (Button)findViewById(R.id.sumButton);
        sumButton.setOnClickListener(sumButtonListener);

        mCallbacks = this;
        loaderManager = getLoaderManager();
    }

    private OnClickListener sumButtonListener  = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
            Log.d(this.getClass().getName(), hashCode() + " click!");
            if(firstNumber.getText().length() == 0
					|| secondNumber.getText().length() == 0) {
                showToastLong(getResources().getString(R.string.login_activity_fill_warning));
			} else {
                final String login = firstNumber.getText().toString();
                final String password = secondNumber.getText().toString();
                String Url = CampusApiURL.getAuth(login, password);
                Bundle authData = new Bundle();
                authData.putString(HttpStringLoader.URL_STRING, Url);
                loaderManager.restartLoader(AUTH_LOADER_ID, authData, mCallbacks).onContentChanged();
			}
		}
	};

    private void checkAuth(HttpResponse httpResponse) {
        final int statusCode = httpResponse.getStatusCode();
        final String response = httpResponse.getEntity();
        if(statusCode == HttpStatus.SC_OK) {
            try {
                //showToastLong(response);
                Authorization authorization = JSONAuthorizationParser.parse(response);
                startPermissionsLoader(authorization.getData());
                startUserLoader(authorization.getData());
                startUserDataLoader(authorization.getData());

            } catch (JSONException e) {
                showToastLong(getResources().getString(R.string.login_activity_json_error));
                Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
            }
        } else {
            showToastLong(getResources().getString(R.string.login_activity_auth_fail));
        }
    }

    private void startPermissionsLoader(String data) {
        startHttpLoader(PERMISSIONS_LOADER_ID,CampusApiURL.getPermission(data));
    }

    private void startUserDataLoader(String data) {
        startHttpLoader(USER_DATA_LOADER_ID,CampusApiURL.getUserData(data,"11"));
    }

    private void startUserLoader(String sessionId) {
        startHttpLoader(USER_LOADER_ID,CampusApiURL.getCurrentUser(sessionId));
    }

    private void startHttpLoader(int id, String url) {
        Bundle permissionsData = new Bundle();
        permissionsData.putString(HttpStringLoader.URL_STRING, url);
        loaderManager.initLoader(id, permissionsData, mCallbacks).onContentChanged();
    }

    private Permissions parsePermissions(HttpResponse httpResponse) {
        final String permissionsStr = httpResponse.getEntity();
        try {
            return JSONGetPermissionsParser.parse(permissionsStr);
        } catch (JSONException e) {
            showToastLong(getResources().getString(R.string.login_activity_json_error));
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        //it`s ok because of checking for null further
        return null;
    }

    private UserData parseUser(HttpResponse httpResponse) {
        final String userDataStr = httpResponse.getEntity();
        try {
            return JSONUserDataParser.parse(userDataStr);
        } catch (JSONException e) {
            showToastLong(getResources().getString(R.string.login_activity_json_error));
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        //it`s ok because of checking for null further
        return null;
    }

    private UserData parseUserData(HttpResponse httpResponse) {
        final String userDataStr = httpResponse.getEntity();
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
        if(permissions != null && userData != null){
            Intent intent = new Intent(getOuter(), MainActivity.class);
            startActivity(intent);
        }
    }

    private LoginActivity getOuter() {
        return this;
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
        Log.d(this.getClass().getName(), hashCode() + " load starts");
        return new HttpStringLoader(LoginActivity.this,bundle.getString(HttpStringLoader.URL_STRING));
    }

    @Override
    public void onLoadFinished(Loader<HttpResponse> httpResponseLoader, HttpResponse httpResponse) {
        int currentLoaderId = httpResponseLoader.getId();
        Log.d(this.getClass().getName(), hashCode() + " load finished (loader)" + currentLoaderId
                + "\n---------\n" + httpResponse);
        switch (currentLoaderId) {
            case AUTH_LOADER_ID:
                checkAuth(httpResponse);
                break;
            case PERMISSIONS_LOADER_ID:
                permissions = parsePermissions(httpResponse);
                startMainActivity();
                break;
            case USER_DATA_LOADER_ID:
                userData = parseUserData(httpResponse);
                startMainActivity();
                break;
            case USER_LOADER_ID:
                currentUserData = parseUser(httpResponse);

                break;
        }


    }


    @Override
    public void onLoaderReset(Loader<HttpResponse> httpResponseLoader) {

    }
}
