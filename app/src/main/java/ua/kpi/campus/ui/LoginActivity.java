package ua.kpi.campus.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import ua.kpi.campus.R;
import ua.kpi.campus.util.Connectivity;
import ua.kpi.campus.util.PrefUtils;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ViewGroup card = (ViewGroup) findViewById(R.id.login_card);
        card.addView(View.inflate(this, R.layout.sign_in_layout, null));
        
        findViewById(R.id.button_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        findViewById(R.id.guest_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.markAuthPassed(getApplicationContext());
                
                card.removeAllViews();
                card.addView(View.inflate(LoginActivity.this, R.layout.group_edit_layout,null));
            }
        });

        final MaterialAutoCompleteTextView textView = (MaterialAutoCompleteTextView) findViewById(R.id.group_input);
        
        String[] groups = getResources().getStringArray(R.array.Groups);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this,
                R.layout.auto_complete_item, groups);
        
        textView.setAdapter(adapter);
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textView.getText().toString().contains("И")) {
                    String str = textView.getText().toString().replace('И', 'І');
                    textView.setText(str);
                    textView.setSelection(str.length());
                }
                if (textView.getText().toString().contains("и")) {
                    String str = textView.getText().toString().replace('и', 'і');
                    textView.setText(str);
                    textView.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        findViewById(R.id.enter_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Connectivity.isConnected(LoginActivity.this)) {
                    
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
