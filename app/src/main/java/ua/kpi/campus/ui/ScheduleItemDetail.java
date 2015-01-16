package ua.kpi.campus.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import ua.kpi.campus.BaseActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.model.ScheduleItem;

public class ScheduleItemDetail extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_item_detail);
        setUpNavDrawer();
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

        mToolbar.setTitle(item.getLessonName() + ", " +
                                  getString(R.string.week).substring(0, 1) +
                                  item.getLessonWeek() + ", " + dayOfWeek);

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
}
