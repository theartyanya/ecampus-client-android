package ua.kpi.campus.Activity;

import android.app.Activity;
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
import ua.kpi.campus.jsonparsers.*;

public class MainActivity extends Activity {
	private EditText firstNumber;
	private EditText secondNumber;
	private Button sumButton;
	private TextView resultText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
        String login = firstNumber.getText().toString();
        String password = secondNumber.getText().toString();
        String response = CampusApi.auth(login, password);
        if(CampusApi.AUTHORIZATION_FAILED.equals(response)){
            showAuthorizeFail();
        } else {
            String session_id = getSessionId(response);
            showSessionId(session_id);
        }
    }

    private void showSessionId(String session_id) {
        Toast.makeText(getApplicationContext(), session_id, Toast.LENGTH_LONG).show();
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
