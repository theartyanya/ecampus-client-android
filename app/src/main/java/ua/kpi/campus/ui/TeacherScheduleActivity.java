package ua.kpi.campus.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ua.kpi.campus.R;
import ua.kpi.campus.api.SyncScheduleTeacher;
import ua.kpi.campus.model.ScheduleItemTeacher;
import ua.kpi.campus.model.TeacherItem;
import ua.kpi.campus.provider.ScheduleProvider;
import ua.kpi.campus.ui.adapters.TeacherScheduleAdapter;
import ua.kpi.campus.ui.fragments.TeacherFragment;
import ua.kpi.campus.ui.widgets.SlidingTabLayout;

/**
 * Created by doroshartyom on 21.01.2015.
 */
public class TeacherScheduleActivity extends ActionBarActivity implements TeacherFragment.Listener, SyncScheduleTeacher.CallBacks {

    SlidingTabLayout slidingTabLayout = null;
    ViewPager viewPager = null;
    MyViewPagerAdapter viewPagerAdapter;

    private static String[] WEEK_NAMES;

    private Set<TeacherFragment> mTeacherFragments = new HashSet<TeacherFragment>();

    private TeacherScheduleAdapter[] mTeacherAdapters = new TeacherScheduleAdapter[2];
    
    ScheduleProvider scheduleProvider;
    private int teacherID;
    private static final String LOG_TAG = "TeacherScheduleActivity";
    private static final String ARG_SCHEDULE_WEEK_INDEX
            = "ua.kpi.campus.ARG_SCHEDULE_WEEK_INDEX";
    
    private ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_schedule);

        TeacherItem item = (TeacherItem)getIntent().getSerializableExtra("teacherItem");
        
        //Working with ActionBar View
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(item.getTeacherName());

        WEEK_NAMES = new String[] {
                getApplicationContext().getString(R.string.week)+" 1",
                getApplicationContext().getString(R.string.week)+" 2"
        };
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        for (int i = 0; i < 2; i++) {
            mTeacherAdapters[i] = new TeacherScheduleAdapter(this);
        }

        scheduleProvider = new ScheduleProvider(getApplicationContext());

        teacherID=item.getTeacherId();


        ArrayList<ScheduleItemTeacher> first = scheduleProvider.getScheduleItemsTeacherFromDatabase(1, item.getTeacherId());
        ArrayList<ScheduleItemTeacher> second = scheduleProvider.getScheduleItemsTeacherFromDatabase(2, item.getTeacherId());

        if(first.size()==0 && second.size()==0){
            SyncScheduleTeacher scheduleTeacher = new SyncScheduleTeacher(this, item.getTeacherId());
            SyncScheduleTeacher.Connect connect = new SyncScheduleTeacher.Connect(this);
            connect.execute();
        }


        mTeacherAdapters[0].updateItems(scheduleProvider.getScheduleItemsTeacherFromDatabase(1, item.getTeacherId()));
        mTeacherAdapters[1].updateItems(scheduleProvider.getScheduleItemsTeacherFromDatabase(2, item.getTeacherId()));

        mTeacherAdapters[0].notifyDataSetChanged();
        mTeacherAdapters[1].notifyDataSetChanged();

        viewPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);


        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        slidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);

        Resources res = getResources();

        setSlidingTabLayoutContentDescriptions();

        slidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.accent));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        if (slidingTabLayout != null) {
            slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                @Override
                public void onPageSelected(int position) {}

                @Override
                public void onPageScrollStateChanged(int state) {}
            });
        }


        //addDataObservers();

    }

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
        
        if (id == R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentViewCreated(ListFragment fragment) {
        fragment.getListView().addHeaderView(
                getLayoutInflater().inflate(R.layout.reserve_action_bar_space_header_view, null));
        int dayIndex = fragment.getArguments().getInt(ARG_SCHEDULE_WEEK_INDEX, 0);
        fragment.setListAdapter(mTeacherAdapters[dayIndex]);
        fragment.setListAdapter(mTeacherAdapters[dayIndex]);


    }

    @Override
    public void onFragmentAttached(TeacherFragment fragment) {
        mTeacherFragments.add(fragment);
    }

    @Override
    public void onFragmentDetached(TeacherFragment fragment) {
        mTeacherFragments.remove(fragment);
    }

    private CharSequence getWeekName(int position) {
        return WEEK_NAMES[position];
    }

    private void setSlidingTabLayoutContentDescriptions() {
        for (int i = 0; i < 2; i++) {
            slidingTabLayout.setContentDescription(i,
                    getString(R.string.my_schedule_tab_desc_a11y, getWeekName(i)));
        }
    }

    protected void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void taskStarted(boolean started) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Schedule");
        progressDialog.show();
    }

    @Override
    public void taskCompleted(boolean completed) {
       progressDialog.dismiss();

        Log.d(LOG_TAG, "taskCompleted");
        ArrayList<ScheduleItemTeacher> first = scheduleProvider.getScheduleItemsTeacherFromDatabase(1, teacherID);
        ArrayList<ScheduleItemTeacher> second = scheduleProvider.getScheduleItemsTeacherFromDatabase(2, teacherID);
        if (!first.isEmpty() && !second.isEmpty()) {
            mTeacherAdapters[0].updateItems(scheduleProvider.getScheduleItemsTeacherFromDatabase(1, teacherID));
            mTeacherAdapters[1].updateItems(scheduleProvider.getScheduleItemsTeacherFromDatabase(2, teacherID));
        } else {
          //  Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show();
        }
       // Toast.makeText(this, "FIRST:" + first.size(), Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, "SECOND:" + second.size(), Toast.LENGTH_SHORT).show();

        mTeacherAdapters[0].notifyDataSetChanged();
        mTeacherAdapters[1].notifyDataSetChanged();
        finish();
        startActivity(getIntent());

        
    }
    public void notifyAdapters() {
        //Updating fragments

        mTeacherAdapters[0].updateItems(scheduleProvider.getScheduleItemsTeacherFromDatabase(1, teacherID));
        mTeacherAdapters[1].updateItems(scheduleProvider.getScheduleItemsTeacherFromDatabase(2, teacherID));

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
    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            Log.d(LOG_TAG, "Creating fragment #" + position);
            TeacherFragment frag = new TeacherFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SCHEDULE_WEEK_INDEX, position);
            frag.setArguments(args);
            return frag;
        }
        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return getWeekName(position);
        }
    }
}
