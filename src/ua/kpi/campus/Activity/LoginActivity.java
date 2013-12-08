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
import ua.kpi.campus.R;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.loaders.HttpStringLoader;
import ua.kpi.campus.loaders.HttpResponse;
import ua.kpi.campus.session.Session;

public class LoginActivity extends Activity implements LoaderManager.LoaderCallbacks<HttpResponse>{
	private EditText firstNumber;
	private EditText secondNumber;
	private Button sumButton;
    private final static int AUTH_LOADER_ID = 101;
    private LoaderManager.LoaderCallbacks<HttpResponse> mCallbacks;
    private LoaderManager loaderManager;

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
                Bundle authData = new Bundle();
                authData.putString(HttpStringLoader.URL_STRING, CampusApiURL.getAuth(login, password));
                loaderManager.restartLoader(AUTH_LOADER_ID, authData, mCallbacks).onContentChanged();
			}
		}
	};


    private void startMainActivity(Session session) {
        Intent intent = new Intent(getOuter(), MainActivity.class);
        startActivity(intent);
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
        Log.d(this.getClass().getName(), hashCode() + " load finished");

        final int statusCode = httpResponse.getStatusCode();
        if(statusCode == HttpStatus.SC_OK) {
            final String response = httpResponse.getEntity();

            showToastLong(response);
        } else {
            showToastLong(getResources().getString(R.string.login_activity_auth_fail));
        }
    }

    @Override
    public void onLoaderReset(Loader<HttpResponse> httpResponseLoader) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
