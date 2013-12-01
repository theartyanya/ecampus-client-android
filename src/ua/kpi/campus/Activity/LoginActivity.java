package ua.kpi.campus.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ua.kpi.campus.CampusApi;
import ua.kpi.campus.R;
import ua.kpi.campus.jsonparsers.Authorization;
import ua.kpi.campus.jsonparsers.JSONAuthorizationParser;

public class LoginActivity extends Activity {
	private EditText firstNumber;
	private EditText secondNumber;
	private Button sumButton;
	private TextView resultText;
    private Authorization authorization;
    public final static String EXTRA_PERMISSIONS = "permissions";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        firstNumber = (EditText)findViewById(R.id.firstNumberEdit);
        secondNumber = (EditText)findViewById(R.id.secondNumberEdit);
        sumButton = (Button)findViewById(R.id.sumButton);
        sumButton.setOnClickListener(sumButtonListener); 
        
    }

    OnClickListener sumButtonListener  = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(firstNumber.getText().length() == 0
					|| secondNumber.getText().length() == 0) {
				Toast.makeText(getApplicationContext(), "Заповните всі поля, будь ласка", Toast.LENGTH_SHORT).show();
			} else {
				authorize();
			}
		}
	};

    private void authorize() {
        final String login = firstNumber.getText().toString();
        final String password = secondNumber.getText().toString();
        new AsyncTask<String, Void, String>()
        {
            @Override public void onPostExecute(String result)
            {
                checkAuthResponse(result);
            }

            @Override
            protected String doInBackground(String... params) {
                return CampusApi.auth(login, password);
            }

        }.execute("");
    }

    private void checkAuthResponse(String result) {
        if(CampusApi.ERROR.equals(result)){
            showAuthorizeFail();
        } else {
            String session_id = getSessionId(result);
            getPermissions(session_id);
        }
    }

    private void getPermissions(final String session_id) {
        new AsyncTask<String, Void, String>()
        {
            @Override public void onPostExecute(String result)
            {
                Intent intent = new Intent(getOuter(), MainActivity.class);
                intent.putExtra(EXTRA_PERMISSIONS, result);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {
                return CampusApi.getPermission(session_id);
            }

        }.execute("");
    }

    private LoginActivity getOuter() {
        return this;
    }

    private String getSessionId(String response) {
        Authorization authorization = JSONAuthorizationParser.parse(response);
        return authorization.getData();
    }

    private void showAuthorizeFail() {
        Toast.makeText(getApplicationContext(), "Авторизація невдала", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
