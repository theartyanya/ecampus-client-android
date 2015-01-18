package ua.kpi.campus.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import ua.kpi.campus.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.api.Auth;
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

    public void signIn(View view) {
        //TODO get off this from here
        PrefUtils.putLoginAndPassword(getApplicationContext(), ((MaterialEditText)findViewById(R.id.login_input)).getText().toString(), ((MaterialEditText)findViewById(R.id.password_input)).getText().toString());
        Auth authClient = new Auth(this);
        authClient.execute();
        try{
            switch (authClient.get()){
                case 200:
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case 403:
                    SnackbarManager.show(
                            Snackbar.with(this)
                                    .text(getString(R.string.invalid_auth_data)));
                    break;
                case 500:
                    SnackbarManager.show(
                            Snackbar.with(this)
                                    .text(getString(R.string.server_is_down)+" (500)"));
                    break;
                case -2:
                    SnackbarManager.show(
                            Snackbar.with(this)
                                    .text(getString(R.string.server_is_down)+" (-2)"));
                    break;
                case -1:
                    SnackbarManager.show(
                            Snackbar.with(this)
                                    .text(getString(R.string.internal_error)+" (-1)"));
                    break;
                case -3:
                    SnackbarManager.show(
                            Snackbar.with(this)
                                    .text(getString(R.string.no_internet)));
                    break;
                default:
                    SnackbarManager.show(
                            Snackbar.with(this)
                                    .text(getString(R.string.internal_error)+" (d)"));
                    break;

            }
        }catch(Exception e){

        }

    }
}
