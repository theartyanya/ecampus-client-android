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
import ua.kpi.campus.api.CampusApi;
import ua.kpi.campus.R;
import ua.kpi.campus.session.Session;

public class LoginActivity extends Activity {
	private EditText firstNumber;
	private EditText secondNumber;
	private Button sumButton;
	private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        firstNumber = (EditText)findViewById(R.id.firstNumberEdit);
        secondNumber = (EditText)findViewById(R.id.secondNumberEdit);
        sumButton = (Button)findViewById(R.id.sumButton);
        sumButton.setOnClickListener(sumButtonListener); 
        
    }

    private OnClickListener sumButtonListener  = new OnClickListener() {
		
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
                if(isLogged(result)){
                    Session session = new Session(result);
                    startMainActivity(session);
                } else {
                    showAuthorizeFail();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                return CampusApi.auth(login, password);
            }

        }.execute("");
    }

    private boolean isLogged(String result) {
        return !CampusApi.ERROR.equals(result);
    }

    private void startMainActivity(Session session) {
        Intent intent = new Intent(getOuter(), MainActivity.class);
        startActivity(intent);
    }

    private LoginActivity getOuter() {
        return this;
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
