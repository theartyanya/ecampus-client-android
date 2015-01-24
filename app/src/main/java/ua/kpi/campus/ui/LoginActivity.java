package ua.kpi.campus.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import ua.kpi.campus.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.api.Auth;
import ua.kpi.campus.api.GetCurrentUser;
import ua.kpi.campus.api.SyncSchedule;
import ua.kpi.campus.model.TeacherItem;
import ua.kpi.campus.util.Connectivity;
import ua.kpi.campus.util.PrefUtils;

public class LoginActivity extends ActionBarActivity implements SyncSchedule.CallBacks, GetCurrentUser.CallBacks, Auth.CallBacks{
    
    MaterialAutoCompleteTextView textView;
    private final String LOG_TAG="LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ViewGroup card = (ViewGroup) findViewById(R.id.login_card);
        card.addView(View.inflate(this, R.layout.sign_in_layout, null));
        
        findViewById(R.id.guest_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.markAuthPassed(getApplicationContext());
                
                card.removeAllViews();
                card.addView(View.inflate(LoginActivity.this, R.layout.group_edit_layout,null));

                textView = (MaterialAutoCompleteTextView) findViewById(R.id.group_input);
                String[] array = getResources().getStringArray(R.array.Groups);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this,
                        R.layout.auto_complete_item, array);
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
        if (Connectivity.isConnected(getApplicationContext())) {
            PrefUtils.putLoginAndPassword(getApplicationContext(), ((MaterialEditText) findViewById(R.id.login_input)).getText().toString(), ((MaterialEditText) findViewById(R.id.password_input)).getText().toString());
            Auth authClient = new Auth(this, this);
            authClient.execute();




        } else {
            SnackbarManager.show(
                    Snackbar.with(getApplicationContext())
                            .text(getResources().getString(R.string.no_internet))
                            .actionLabel(getResources().getString(R.string.settings))
                            .actionColor(getResources().getColor(R.color.primary))
                            .actionListener(new ActionClickListener() {
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                                }
                            })
                            .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                    , this);
        }
    }

    public void guestSignIn(View view) {
        if (Connectivity.isConnected(getApplicationContext())) {
            PrefUtils.putPrefStudyGroupName(this, textView.getText().toString());
            SyncSchedule sync = SyncSchedule.getSyncSchedule(PrefUtils.getPrefStudyGroupName(this), this);
            SyncSchedule.Connect connect = new SyncSchedule.Connect(this);
            connect.execute(this);
            PrefUtils.markScheduleUploaded(this);
        } else {
            SnackbarManager.show(
                    Snackbar.with(getApplicationContext())
                            .text(getResources().getString(R.string.no_internet))
                            .actionLabel(getResources().getString(R.string.settings))
                            .actionColor(getResources().getColor(R.color.primary))
                            .actionListener(new ActionClickListener() {
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                                }
                            })
                            .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                    , this);
        }
        
    }
    @Override
    public void AuthCompleted(boolean completed) {
        GetCurrentUser gcu = new GetCurrentUser(this, this); //first 'this' is context, second is callback
        gcu.execute();
    }


    @Override
    public void GetCurrentUserCompleted(boolean completed){
        if(PrefUtils.isStudent(this)){
            SyncSchedule.Connect connect = new SyncSchedule.Connect(this);
            connect.execute(this);
        }
        else{
            Intent intent = new Intent(LoginActivity.this, TeacherScheduleActivity.class);
            TeacherItem teacherItem = new TeacherItem();
            teacherItem.setTeacherName(PrefUtils.getPrefStudyFullname(this));
            teacherItem.setTeacherId(PrefUtils.getMyId(this));
            intent.putExtra("teacherItem", teacherItem);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void syncScheduleCompleted(boolean completed) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }





}
