package ua.kpi.campus.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ua.kpi.campus.BaseActivity;
import ua.kpi.campus.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.provider.ScheduleProvider;

/**
 * Created by Admin on 17.01.2015.
 */
public class TeacherActivity extends BaseActivity {

    private List<String> spinnerList = new ArrayList<String>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        super.setUpNavDrawer();
        super.scheduleSyncer();

        spinnerList.add(getString(R.string.schedule));
        spinnerList.add(getString(R.string.teachers));
        
        Toolbar toolbar = mToolbar;

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        View spinnerContainer = LayoutInflater.from(this).inflate(R.layout.toolbar_spinner,
                toolbar, false);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toolbar.addView(spinnerContainer, lp);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext());
        spinnerAdapter.addItems(spinnerList);

        Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);
        spinner.setAdapter(spinnerAdapter);
        
        spinner.setSelection(1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch(position){
                    case 0:
                        Intent intent = new Intent(TeacherActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case 1:
                        //Do nothing
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //do nothing
            }
        });

        ScheduleProvider scheduleProvider = new ScheduleProvider(getApplicationContext());

        ListView list = (ListView) findViewById(R.id.list_view);
        TeacherAdapter adapter = new TeacherAdapter(this);
        list.setAdapter(adapter);
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            startSettingsActivity();

        return super.onOptionsItemSelected(item);
    }
}
