package ua.kpi.campus;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ua.kpi.campus.api.SyncSchedule;
import ua.kpi.campus.ui.LoginActivity;
import ua.kpi.campus.ui.SettingsActivity;
import ua.kpi.campus.ui.WelcomeActivity;
import ua.kpi.campus.util.PrefUtils;
import ua.kpi.campus.util.ShakeListener;

import java.util.ArrayList;

/**
 * This activity is designed to reduce copypaste code for
 * navigation drawer initialization. Will be useful further,
 * implementing tracking services like Crashlytics or Flurry
 *
 * infm created it with love on 1/16/15. Enjoy ;)
 */
public class BaseActivity extends ActionBarActivity {
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
	protected static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
			R.string.navdrawer_item_my_schedule,   // Schedule
			R.string.navdrawer_item_my_tasks,      // Tasks
			R.string.navdrawer_item_my_lessons,    // Lessons
			R.string.navdrawer_item_my_notes,      // Notes
			R.string.navdrawer_item_settings,      // Settings
			R.string.navdrawer_item_feedback                             // Feedback
	};

	//icons for navdrawer items
	protected static final int[] NAVDRAWER_ICON_RES_ID = new int[]{
			R.drawable.ic_schedule_24dp,
			R.drawable.ic_trending_up_24dp,
			R.drawable.ic_school_24dp,
			R.drawable.ic_mode_edit_24dp,
			R.drawable.ic_settings_24dp,
			R.drawable.ic_help_24dp
	};

	// delay to launch nav drawer item, to allow close animation to play
	protected static final int NAVDRAWER_LAUNCH_DELAY = 250;

	// fade in and fade out durations for the main content when switching between
	// different Activities of the app through the Nav Drawer
	//private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
	//private static final int MAIN_CONTENT_FADEIN_DURATION = 250;

	// list of navdrawer items that were actually added to the navdrawer, in order
	private ArrayList<Integer> mNavDrawerItems = new ArrayList<>();

	// views that correspond to each navdrawer item, null if not yet created
	protected View[] mNavDrawerItemViews = null;

	protected DrawerLayout mDrawerLayout;
	protected Toolbar mToolbar;
	protected Handler mHandler;

	// The following are used for the shake detection
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeListener shakeListener;

    private String LOG_TAG = "BaseActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        
        
		checkForAgreement();
        checkForLoginDone();
		setUpShakeListener();
        
		//Initializing mHandler
		mHandler = new Handler();
	}

    public static void setAccessibilityIgnore(View view) {
		view.setClickable(false);
		view.setFocusable(false);
		view.setContentDescription("");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
		}
	}

	/**
	 * Sets up the navigation drawer as appropriate. Note that the nav drawer will be
	 * different depending on whether the attendee indicated that they are attending the
	 * event on-site vs. attending remotely.
	 */
	protected void setUpNavDrawer() {
		//Trying to setup NavDrawer
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);

		ActionBar bar = getSupportActionBar();
		if (bar != null)
			bar.setDisplayHomeAsUpEnabled(true);

		//Getting NavDrawer self item
		//int selfItem = getSelfNavDrawerItem();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		//Checking mDrawerLayout is not null
		if (mDrawerLayout == null) {

			Toast.makeText(getApplicationContext(), "NULL", Toast.LENGTH_SHORT).show();
			return;
		}
		//This shit code may just not work
        /*if (selfItem == NAVDRAWER_ITEM_INVALID) {
            View navDrawer = (View) findViewById(R.id.navdrawer);
            if (navDrawer != null) {
                ((ViewGroup) navDrawer.getParent()).removeView(navDrawer);
            }
            //mDrawerLayout = null;
            return;
        } */

		//Initializing Nav Drawer Toggle
		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
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
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		//Syncs Toggle
		mDrawerToggle.syncState();

	}

	private void setUpShakeListener(){
		//ShakeListener Setup
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		shakeListener = new ShakeListener();
		shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
			@Override
			public void onShake(int count){
                /*Implementing shake listener. When user shakes their device,
                 *we need to show them a dialog with feedback
                 */
				//TODO: implement th feedback activity

				Toast.makeText(getApplicationContext(), "Shake", Toast.LENGTH_SHORT).show();
				giveFeedback();
			}
		});
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
		if (itemId != getSelfNavDrawerItem()){
			// launch the target Activity after a short delay, to allow the close animation to play
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run(){
					goToNavDrawerItem(itemId);
				}
			}, NAVDRAWER_LAUNCH_DELAY);

			// change the active item on the list so the user can see the item changed
			// fade out the main content
            /*View mainContent = findViewById(R.id.main_content);
            if (mainContent != null) {
                mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
            }*/
		}
		setSelectedNavDrawerItem(itemId);
		mDrawerLayout.closeDrawer(Gravity.START);
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
				startSettingsActivity();
				Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
				break;
			case NAVDRAWER_ITEM_FEEDBACK:
				//intent = new Intent(this, VideoLibraryActivity.class);
				//startActivity(intent);
				//finish();
				Toast.makeText(getApplicationContext(), "Feedback", Toast.LENGTH_SHORT).show();
				giveFeedback();
				break;
		}
	}

	/**
	 * This code will be reused since we need
	 * proper navigation drawer animation
	 * @param that : class of Activity, which will be started
	 */
	protected void startTopLevelActivity(Context context, Class that){
		startActivity(new Intent(context, that));
		finish();
	}

	protected void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

	private void giveFeedback(){
		try {
			Resources resources = getResources();
			startActivity(Intent.createChooser(new Intent(Intent.ACTION_SENDTO,
														  Uri.parse(
																  resources.getString(R.string.mail_to_support)
														  )), resources.getString(R.string.navdrawer_item_feedback)));
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, R.string.email_app_not_found, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// Add the following line to register the Session Manager Listener onResume
		mSensorManager.registerListener(shakeListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		// Add the following line to unregister the Sensor Manager onPause
		mSensorManager.unregisterListener(shakeListener);
		super.onPause();
	}

	private void checkForAgreement(){
		if (!PrefUtils.isTosAccepted(this))
			startTopLevelActivity(this, WelcomeActivity.class);
	}
    
    protected void scheduleSyncer() {
        if (!PrefUtils.isScheduleUploaded(this) && PrefUtils.isTosAccepted(this)) {
            Log.d(LOG_TAG, "Starting syncing schedule");
            String groupName = PrefUtils.getPrefStudyGroupName(this);
            Log.i(LOG_TAG, groupName);
            SyncSchedule sync = SyncSchedule.getSyncSchedule(groupName, getApplicationContext());

            SyncSchedule.Connect connect = new SyncSchedule.Connect();
            connect.execute(this);
            PrefUtils.markScheduleUploaded(this);
        }
    }
    
    protected void checkForLoginDone() {
        if (!PrefUtils.isLoginDone(this)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        
    }
}
