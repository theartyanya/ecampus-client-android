package ua.kpi.campus;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
//hello commit Serge1
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
			// TODO Auto-generated method stub
			// test...
			if(firstNumber.getText().length() == 0 
					|| secondNumber.getText().length() == 0) {
				Toast.makeText(getApplicationContext(), "���� �����, ��������� �� ����", Toast.LENGTH_SHORT).show();
			} else {
				String login = firstNumber.getText().toString();
				String password = secondNumber.getText().toString();			
				Toast.makeText(getApplicationContext(), "����������� �������", Toast.LENGTH_SHORT).show();
				CampusApi.login(login,password);
			}
		}
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
