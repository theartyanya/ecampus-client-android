package ua.kpi.campus;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import ua.kpi.campus.api.SyncSchedule;
import ua.kpi.campus.provider.ScheduleProvider;
import ua.kpi.campus.ui.ScheduleAdapter;
import ua.kpi.campus.ui.ScheduleFragment;
import ua.kpi.campus.ui.widgets.SlidingTabLayout;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends BaseActivity implements ScheduleFragment.Listener {

    //private LinearLayout mAccountListContainer;
    //private ImageView mExpandAccountBoxIndicator;
    //private boolean mAccountBoxExpanded = false;
    //private static final int ACCOUNT_BOX_EXPAND_ANIM_DURATION = 200;

    ScrollView scrollView;
    SlidingTabLayout slidingTabLayout = null;
    ViewPager viewPager = null;
    MyViewPagerAdapter viewPagerAdapter;
    private Set<ScheduleFragment> mScheduleFragments = new HashSet<ScheduleFragment>();

    //private ActionBarDrawerToggle drawerToggle;

    //SwipeRefreshLayout refreshLayout = null;

    private static String[] WEEK_NAMES;

    //Log Tag for app
    //private static final String LOG_TAG = "lol";

    //private ImageLoader mImageLoader;

    private final String LOG_TAG = "MainActivity";
    private static final String ARG_SCHEDULE_WEEK_INDEX
            = "ua.kpi.campus.ARG_SCHEDULE_WEEK_INDEX";

    private ScheduleAdapter[] mScheduleAdapters = new ScheduleAdapter[2];

    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.setUpNavDrawer(); //we can now findById our views

        WEEK_NAMES = new String[] {
                getApplicationContext().getString(R.string.week)+" 1",
                getApplicationContext().getString(R.string.week)+" 2"
        };
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        for (int i = 0; i < 2; i++) {
            mScheduleAdapters[i] = new ScheduleAdapter(this);
        }

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        SyncSchedule sync = SyncSchedule.getSyncSchedule("IK-31", getApplicationContext());
        SyncSchedule.Connect connect = new SyncSchedule.Connect();
        connect.execute();

        if (!welcomeScreenShown) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.apply(); //let it be async
        }

        ScheduleProvider scheduleProvider = new ScheduleProvider(getApplicationContext());

        mScheduleAdapters[0].updateItems(scheduleProvider.getScheduleItemsFromDatabase(1));
        mScheduleAdapters[1].updateItems(scheduleProvider.getScheduleItemsFromDatabase(2));

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
    public void onFragmentViewCreated(ListFragment fragment) {
        fragment.getListView().addHeaderView(
                getLayoutInflater().inflate(R.layout.reserve_action_bar_space_header_view, null));
        int dayIndex = fragment.getArguments().getInt(ARG_SCHEDULE_WEEK_INDEX, 0);
        fragment.setListAdapter(mScheduleAdapters[dayIndex]);
        fragment.setListAdapter(mScheduleAdapters[dayIndex]);
    }

    @Override
    public void onFragmentAttached(ScheduleFragment fragment) {
        mScheduleFragments.add(fragment);
    }

    @Override
    public void onFragmentDetached(ScheduleFragment fragment) {
        mScheduleFragments.remove(fragment);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            Log.d(LOG_TAG, "Creating fragment #" + position);
            ScheduleFragment frag = new ScheduleFragment();
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

    private CharSequence getWeekName(int position) {
        return WEEK_NAMES[position];
    }

    private void setSlidingTabLayoutContentDescriptions() {
        for (int i = 0; i < 2; i++) {
            slidingTabLayout.setContentDescription(i,
                    getString(R.string.my_schedule_tab_desc_a11y, getWeekName(i)));
        }
    }

}