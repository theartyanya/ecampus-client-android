package ua.kpi.campus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import ua.kpi.campus.R;
import ua.kpi.campus.model.ScheduleItem;

public class ScheduleItemDetail extends ActionBarActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_item_detail);

        //init toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        
        initViews();
    }


    private void initViews(){
        ScheduleItem item = (ScheduleItem)getIntent().getSerializableExtra("scheduleItem");
        String dayOfWeek = null;
        switch (item.getDayNumber()){
            case 1:
                dayOfWeek = getResources().getString(R.string.monday);
                break;
            case 2:
                dayOfWeek = getResources().getString(R.string.tuesday);
                break;
            case 3:
                dayOfWeek = getResources().getString(R.string.wednesday);
                break;
            case 4:
                dayOfWeek = getResources().getString(R.string.thursday);
                break;
            case 5:
                dayOfWeek = getResources().getString(R.string.friday);
                break;
            case 6:
                dayOfWeek = getResources().getString(R.string.saturday);
                break;
        }

        //setting title for toolbar
        toolbar.setTitle(item.getLessonName() + ", " +
                                  getString(R.string.week).substring(0, 1) +
                                  item.getLessonWeek() + ", " + dayOfWeek);
        
        //setting supportActionBar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        ((TextView) findViewById(R.id.week)).setText(dayOfWeek); //abundant object creation will slow down gc
        ((TextView) findViewById(R.id.lessonName)).setText(item.getLessonName());
        ((TextView) findViewById(R.id.audience)).setText(item.getLessonRoom());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule_item_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings)
            startSettingsActivity();

        return super.onOptionsItemSelected(item);
    }

    protected void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
