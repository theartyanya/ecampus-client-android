package ua.kpi.campus;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import ua.kpi.campus.ui.widgets.SlidingTabLayout;
import ua.kpi.campus.util.ShakeListener;


public class MainActivity extends ActionBarActivity {

    private ActionBar bar = null;

    //Account box views
    private ViewGroup mDrawerItemsListContainer;
    private LinearLayout mAccountListContainer;
    private ImageView mExpandAccountBoxIndicator;
    private boolean mAccountBoxExpanded = false;
    private static final int ACCOUNT_BOX_EXPAND_ANIM_DURATION = 200;

    ScrollView scrollView;
    SlidingTabLayout slidingTabLayout = null;
    ViewPager viewPager = null;
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    SwipeRefreshLayout refreshLayout = null;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeListener shakeListener;
    private Toolbar mToolbar;

    // list of navdrawer items that were actually added to the navdrawer, in order
    private ArrayList<Integer> mNavDrawerItems = new ArrayList<Integer>();

    //NavDrawer Items
    protected static final int NAVDRAWER_ITEM_MY_SCHEDULE = 0;
    protected static final int NAVDRAWER_ITEM_MY_TASKS = 1;
    protected static final int NAVDRAWER_ITEM_MY_LESSONS = 2;
    protected static final int NAVDRAWER_ITEM_MY_NOTES = 3;
    protected static final int NAVDRAWER_ITEM_SETTINGS = 4;
    protected static final int NAVDRAWER_ITEM_FEEDBACK = 5;
    protected static final int NAVDRAWER_ITEM_INVALID = -1;
    protected static final int NAVDRAWER_ITEM_SEPARATOR = -2;

    //Titles for NavDrawer items
    private static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.navdrawer_item_my_schedule,   // Schedule
            R.string.navdrawer_item_my_tasks,      // Tasks
            R.string.navdrawer_item_my_lessons,    // Lessons
            R.string.navdrawer_item_my_notes,      // Notes
            R.string.navdrawer_item_settings,      // Settings
            R.string.navdrawer_item_feedback       // Feedback
    };

    //icons for inavdrawer items
    private static final int[] NAVDRAWER_ICON_RES_ID = new int[]{
            R.drawable.ic_schedule_24dp,
            R.drawable.ic_trending_up_24dp,
            R.drawable.ic_school_24dp,
            R.drawable.ic_mode_edit_24dp,
            R.drawable.ic_settings_24dp,
            R.drawable.ic_help_24dp
    };

    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;

    // fade in and fade out durations for the main content when switching between
    // different Activities of the app through the Nav Drawer
    private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
    private static final int MAIN_CONTENT_FADEIN_DURATION = 250;

    // views that correspond to each navdrawer item, null if not yet created
    private View[] mNavDrawerItemViews = null;

    private ActionBarDrawerToggle mDrawerToggle;

    //Log Tag for app
    private static final String LOG_TAG = "lol";

    private Handler mHandler;

    //private ImageLoader mImageLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
