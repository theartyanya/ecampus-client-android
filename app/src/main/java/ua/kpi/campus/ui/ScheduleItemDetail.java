package ua.kpi.campus.ui;

import android.content.Intent;
import android.net.IpPrefix;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ua.kpi.campus.R;
import ua.kpi.campus.api.Auth;
import ua.kpi.campus.api.GetCurrentUser;
import ua.kpi.campus.model.ScheduleItem;
import ua.kpi.campus.util.PrefUtils;

public class ScheduleItemDetail extends ActionBarActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private final String LOG_TAG = "ScheduleItemDetail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_item_detail);

        //init toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initViews();
        
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Log.d(LOG_TAG, GetCurrentUser.getFaculty(this));
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
        toolbar.setTitle(dayOfWeek + ", " +
                         getString(R.string.week)+" " +item.getLessonWeek());
        
        //setting supportActionBar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        //((TextView) findViewById(R.id.week)).setText(dayOfWeek); //abundant object creation will slow down gc
        ((TextView) findViewById(R.id.lessonName)).setText(item.getLessonName());
        ((TextView) findViewById(R.id.audience)).setText(item.getLessonRoom());


        //calculating Time
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Calendar currentTime = Calendar.getInstance();
            currentTime.add(Calendar.DAY_OF_WEEK,-1);
            Calendar lessonStart = Calendar.getInstance();
            //TODO
            //parity of the week
            //maybe some optimization
            lessonStart.setTimeInMillis(System.currentTimeMillis());
            lessonStart.set(Calendar.HOUR_OF_DAY,sdf.parse(item.getTimeStart()).getHours());
            lessonStart.set(Calendar.MINUTE,sdf.parse(item.getTimeStart()).getMinutes());
            lessonStart.set(Calendar.DAY_OF_WEEK,item.getDayNumber());

            Calendar lessonEnd = Calendar.getInstance();
            lessonEnd.setTimeInMillis(System.currentTimeMillis());
            lessonEnd.set(Calendar.HOUR_OF_DAY,sdf.parse(item.getTimeEnd()).getHours());
            lessonEnd.set(Calendar.MINUTE, sdf.parse(item.getTimeEnd()).getMinutes());
            lessonEnd.set(Calendar.DAY_OF_WEEK,item.getDayNumber());

            /*DEBUG INFO
            Log.i("timeStart",lessonStart.get(Calendar.DAY_OF_WEEK)+"-day, "+lessonStart.get(Calendar.HOUR_OF_DAY)+":"+lessonStart.get(Calendar.MINUTE));
            Log.i("timeEnd",lessonEnd.get(Calendar.DAY_OF_WEEK)+"-day, "+lessonEnd.get(Calendar.HOUR_OF_DAY)+":"+lessonEnd.get(Calendar.MINUTE));
            Log.i("timeCurrent",currentTime.get(Calendar.DAY_OF_WEEK)+"-day, "+currentTime.get(Calendar.HOUR_OF_DAY)+":"+currentTime.get(Calendar.MINUTE));

            Log.i("timeStartInMillis",Long.toString(lessonStart.getTimeInMillis()));
            Log.i("timeEndInMillis",Long.toString(lessonEnd.getTimeInMillis()));
            Log.i("timeCurrentInMillis",Long.toString(currentTime.getTimeInMillis()));
            */

            //check is lesson in progress
            if(currentTime.getTimeInMillis()>lessonStart.getTimeInMillis() && currentTime.getTimeInMillis()<lessonEnd.getTimeInMillis()){
                Calendar timeToLessonEnd = Calendar.getInstance();
                timeToLessonEnd.setTimeInMillis(lessonEnd.getTimeInMillis()-currentTime.getTimeInMillis());
                ((TextView)findViewById(R.id.timeToLesson)).setText(
                        timeToLessonEnd.get(Calendar.HOUR_OF_DAY)+getString(R.string.hour).substring(0,1)+" "
                                +timeToLessonEnd.get(Calendar.MINUTE)+getString(R.string.minute).substring(0,1));
                //calculating progressBar
                long lessonProgress =100L*((currentTime.get(Calendar.HOUR_OF_DAY)*60+currentTime.get(Calendar.MINUTE))-(lessonStart.get(Calendar.HOUR_OF_DAY)*60+lessonStart.get(Calendar.MINUTE)))
                        /((lessonEnd.get(Calendar.HOUR_OF_DAY)*60+lessonEnd.get(Calendar.MINUTE))-(lessonStart.get(Calendar.HOUR_OF_DAY)*60+lessonStart.get(Calendar.MINUTE)));
                ((ProgressBar)findViewById(R.id.lessonProgressBar)).setProgress((int)lessonProgress);
            }else{
                ((TextView)findViewById(R.id.timeToLesson)).setVisibility(View.GONE);
                ((ProgressBar)findViewById(R.id.lessonProgressBar)).setVisibility(View.GONE);
            }




        }catch(ParseException e){
            e.printStackTrace();
        }
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

    public void auth(View view) {
        Auth.exit(this);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
