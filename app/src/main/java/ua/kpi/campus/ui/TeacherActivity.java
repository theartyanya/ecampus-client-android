package ua.kpi.campus.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import java.util.ArrayList;
import java.util.List;

import ua.kpi.campus.BaseActivity;
import ua.kpi.campus.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.api.SyncSchedule;
import ua.kpi.campus.provider.ScheduleProvider;
import ua.kpi.campus.ui.adapters.SpinnerAdapter;
import ua.kpi.campus.ui.adapters.TeacherAdapter;
import ua.kpi.campus.util.PrefUtils;

/**
 * Created by doroshartyom on 17.01.2015.
 */
public class TeacherActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SyncSchedule.CallBacks {

    private List<String> spinnerList = new ArrayList<String>();
    
    SwipeRefreshLayout refreshLayout;
    TeacherAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        super.checkForLoginDone();
        super.setUpNavDrawer();
        super.scheduleSyncer();

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        refreshLayout.setColorScheme(R.color.green ,R.color.red, R.color.blue, R.color.orange);
        refreshLayout.setOnRefreshListener(this);

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
        adapter = new TeacherAdapter(this);
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

    @Override
    public void onRefresh() {
        SyncSchedule sync = SyncSchedule.getSyncSchedule(PrefUtils.getPrefStudyGroupName(this), getApplicationContext());

        SyncSchedule.Connect connect = new SyncSchedule.Connect(this);
        connect.execute(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void syncScheduleCompleted(boolean completed) {
        refreshLayout.setRefreshing(false);

        SnackbarManager.show(
                Snackbar.with(getApplicationContext())
                        .text(getResources().getString(R.string.up_to_date))
                        .actionLabel(getResources().getString(R.string.ok))
                        .actionColor(getResources().getColor(R.color.primary))
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                            }
                        })
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                , this);
    }
}
