package ua.kpi.campus.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ua.kpi.campus.R;
import ua.kpi.campus.model.ScheduleItem;

public class ScheduleItemDetail extends ActionBarActivity {

    DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    // list of navdrawer items that were actually added to the navdrawer, in order
    private ArrayList<Integer> mNavDrawerItems = new ArrayList<>();
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
            R.string.navdrawer_item_feedback                             // Feedback
    };
    private static String[] WEEK_NAMES;
    //icons for navdrawer items
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
    // views that correspond to each navdrawer item, null if not yet created
    private View[] mNavDrawerItemViews = null;
    SharedPreferences mPrefs;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_item_detail);
        mHandler = new Handler();
        //Trying to setup NavDrawer
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ScheduleItem item = (ScheduleItem)getIntent().getSerializableExtra("scheduleItem");
        String dayOfWeek =null;
        switch(item.getDayNumber()){
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
        mToolbar.setTitle(item.getLessonName()+", " +getString(R.string.week).substring(0,1)+item.getLessonWeek()+", "+dayOfWeek);
        setSupportActionBar(mToolbar);
        TextView weekText = (TextView) findViewById(R.id.week);
        weekText.setText(dayOfWeek);
        TextView lessonNameText = (TextView) findViewById(R.id.lessonName);
        lessonNameText.setText(item.getLessonName());
        TextView audienceText = (TextView) findViewById(R.id.audience);
        audienceText.setText(item.getLessonRoom());

        ActionBar bar = getSupportActionBar();
        if (bar !=null)
            bar.setDisplayHomeAsUpEnabled(true);

        setUpNavDrawer();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule_item_detail, menu);
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
            Intent intent = new Intent(ScheduleItemDetail.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpNavDrawer() {
        //Getting NavDrawer self item
        //int selfItem = getSelfNavDrawerItem();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Checking drawerLayout is not null
        if (drawerLayout == null) {

            Toast.makeText(getApplicationContext(), "NULL", Toast.LENGTH_SHORT).show();
            return;
        }
        //This shit code may just not work
        /*if (selfItem == NAVDRAWER_ITEM_INVALID) {
            View navDrawer = (View) findViewById(R.id.navdrawer);
            if (navDrawer != null) {
                ((ViewGroup) navDrawer.getParent()).removeView(navDrawer);
            }
            //drawerLayout = null;
            return;
        } */

        //Initializing Nav Drawer Toggle
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {

                invalidateOptionsMenu();

            }

            public void onDrawerOpened(View drawerView) {

                invalidateOptionsMenu();

            }

        };

        //Adding items to  Nav Drawer
        populateNavDrawer();

        //Adding "hamburger" icon to Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Adding Nav Drawer Listener
        drawerLayout.setDrawerListener(mDrawerToggle);

        //Syncs Toggle
        mDrawerToggle.syncState();

    }
    private void populateNavDrawer() {

        //Clearing Nav Drawer Items
        mNavDrawerItems.clear();

        //Adding new items to Nav Drawer
        mNavDrawerItems.add(NAVDRAWER_ITEM_MY_SCHEDULE);
        mNavDrawerItems.add(NAVDRAWER_ITEM_MY_TASKS);
        mNavDrawerItems.add(NAVDRAWER_ITEM_MY_LESSONS);
        mNavDrawerItems.add(NAVDRAWER_ITEM_MY_NOTES);
        mNavDrawerItems.add(NAVDRAWER_ITEM_SEPARATOR);
        mNavDrawerItems.add(NAVDRAWER_ITEM_SETTINGS);
        mNavDrawerItems.add(NAVDRAWER_ITEM_FEEDBACK);

        //Committing changes
        createNavDrawerItems();
    }
    //Method, creating Nav Drawer items
    private void createNavDrawerItems() {
        //Initializing  List Container
        ViewGroup mDrawerItemsListContainer = (ViewGroup) findViewById(R.id.navdrawer_items_list);

        //Checking is it not null
        if (mDrawerItemsListContainer == null) {
            return;
        }

        //Initializing View
        mNavDrawerItemViews = new View[mNavDrawerItems.size()];

        //Removing all Views
        mDrawerItemsListContainer.removeAllViews();

        //Creating new View
        int i = 0;
        for (int itemId : mNavDrawerItems) {
            mNavDrawerItemViews[i] = makeNavDrawerItem(itemId, mDrawerItemsListContainer);
            mDrawerItemsListContainer.addView(mNavDrawerItemViews[i]);
            ++i;
        }
    }
    //Method is used to create new Nav Drawer items
    private View makeNavDrawerItem(final int itemId, ViewGroup container) {
        boolean selected = getSelfNavDrawerItem() == itemId;
        int layoutToInflate;
        if (itemId == NAVDRAWER_ITEM_SEPARATOR) {
            layoutToInflate = R.layout.navdrawer_separator;
        } else {
            layoutToInflate = R.layout.navdrawer_item;
        }
        View view = getLayoutInflater().inflate(layoutToInflate, container, false);

        if (isSeparator(itemId)) {
            // we are done
            setAccessibilityIgnore(view);
            return view;
        }

        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        int iconId = itemId >= 0 && itemId < NAVDRAWER_ICON_RES_ID.length ?
                NAVDRAWER_ICON_RES_ID[itemId] : 0;
        int titleId = itemId >= 0 && itemId < NAVDRAWER_TITLE_RES_ID.length ?
                NAVDRAWER_TITLE_RES_ID[itemId] : 0;

        // set icon and text
        iconView.setVisibility(iconId > 0 ? View.VISIBLE : View.GONE);
        if (iconId > 0) {
            iconView.setImageResource(iconId);
        }
        titleView.setText(getString(titleId));

        formatNavDrawerItem(view, itemId, selected);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavDrawerItemClicked(itemId);
            }
        });

        return view;
    }
    private void formatNavDrawerItem(View view, int itemId, boolean selected) {
        if (isSeparator(itemId)) {
            // not applicable
            return;
        }

        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);

        // configure its appearance according to whether or not it's selected
        titleView.setTextColor(selected ?
                getResources().getColor(R.color.navdrawer_text_color_selected) :
                getResources().getColor(R.color.navdrawer_text_color));
        iconView.setColorFilter(selected ?
                getResources().getColor(R.color.navdrawer_icon_tint_selected) :
                getResources().getColor(R.color.navdrawer_icon_tint));
    }
    //Return "true" if item is separator
    private boolean isSeparator(int itemId) {
        return itemId == NAVDRAWER_ITEM_SEPARATOR;
    }

    //Return Self Nav Drawer item
    protected int getSelfNavDrawerItem() {

        return NAVDRAWER_ITEM_MY_SCHEDULE;
    }


    private void onNavDrawerItemClicked(final int itemId) {
        if (itemId == getSelfNavDrawerItem()) {
            drawerLayout.closeDrawer(Gravity.START);
            return;
        }

        // launch the target Activity after a short delay, to allow the close animation to play
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNavDrawerItem(itemId);
            }
        }, NAVDRAWER_LAUNCH_DELAY);

        // change the active item on the list so the user can see the item changed
        setSelectedNavDrawerItem(itemId);
        // fade out the main content
            /*View mainContent = findViewById(R.id.main_content);
            if (mainContent != null) {
                mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
            }*/


        drawerLayout.closeDrawer(Gravity.START);
    }

    private void setSelectedNavDrawerItem(int itemId) {
        if (mNavDrawerItemViews != null) {
            for (int i = 0; i < mNavDrawerItemViews.length; i++) {
                if (i < mNavDrawerItems.size()) {
                    int thisItemId = mNavDrawerItems.get(i);
                    formatNavDrawerItem(mNavDrawerItemViews[i], thisItemId, itemId == thisItemId);
                }
            }
        }
    }
    public static void setAccessibilityIgnore(View view) {
        view.setClickable(false);
        view.setFocusable(false);
        view.setContentDescription("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        }
    }

    //Going to new Activities via Nav Drawer
    private void goToNavDrawerItem(int item) {
        //Intent intent;
        switch (item) {
            case NAVDRAWER_ITEM_MY_SCHEDULE:
                //intent = new Intent(this, MyScheduleActivity.class);
                //startActivity(intent);
                //finish();
                Toast.makeText(getApplicationContext(), "Schedule", Toast.LENGTH_SHORT).show();
                break;
            case NAVDRAWER_ITEM_MY_TASKS:
                //intent = new Intent(this, BrowseSessionsActivity.class);
                //startActivity(intent);
                //finish();
                Toast.makeText(getApplicationContext(), "Tasks", Toast.LENGTH_SHORT).show();
                break;
            case NAVDRAWER_ITEM_MY_LESSONS:
                //intent = new Intent(this, UIUtils.getMapActivityClass(this));
                //startActivity(intent);
                //finish();
                Toast.makeText(getApplicationContext(), "Lessons", Toast.LENGTH_SHORT).show();
                break;
            case NAVDRAWER_ITEM_MY_NOTES:
                //intent = new Intent(this, SocialActivity.class);
                //startActivity(intent);
                //finish();
                Toast.makeText(getApplicationContext(), "Notes", Toast.LENGTH_SHORT).show();
                break;
            case NAVDRAWER_ITEM_SETTINGS:
                //intent = new Intent(this, SettingsActivity.class);
                //startActivity(intent);
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                break;
            case NAVDRAWER_ITEM_FEEDBACK:
                //intent = new Intent(this, VideoLibraryActivity.class);
                //startActivity(intent);
                //finish();
                Toast.makeText(getApplicationContext(), "Feedback", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
