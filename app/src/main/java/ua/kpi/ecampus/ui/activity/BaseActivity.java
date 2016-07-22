package ua.kpi.ecampus.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import ua.kpi.ecampus.CampusApplication;
import ua.kpi.ecampus.di.ActivityModule;
import ua.kpi.campus.R;
import ua.kpi.campus.ui.adapter.CustomDrawerAdapter;
import ua.kpi.campus.ui.view.DrawerItem;

/**
 * Base activity created to be extended by every activity class.
 * This class provides dependency injection configuration, ButterKnife
 * Android library configuration and some methods common to every activity.
 * <p>
 * Created by Administrator on 26.01.2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ObjectGraph objectGraph;

    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // navigation drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    private List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
    }

    /*
    This method for create DrawerLayout in ContentView and open it in all activities
     */
    @Override
    public void setContentView(int layoutResID) {
        initialiseListDrawer();

        mTitle = getTitle();
        mDrawerTitle = getResources().getString(R.string.menu);


        DrawerLayout myDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(ua.kpi.campus.R.layout.drawer_layout, null);
        FrameLayout activityContainer = (FrameLayout) myDrawerLayout.findViewById(ua.kpi.campus.R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        mDrawerList = (ListView) myDrawerLayout.findViewById(R.id.list_left_drawer);
        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.drawer_lauout_header_6, null, false);
        mDrawerList.addHeaderView(listHeaderView);

        mDrawerList.setAdapter(new CustomDrawerAdapter(this,
                ua.kpi.campus.R.layout.drawer_layout_list_item_2, dataList));

        initialisemDrawerToggle();
      /*
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());*/

        super.setContentView(myDrawerLayout);
        //Toolbar toolbar = (Toolbar) findViewById(ua.kpi.campus.R.id.toolbar);
        // setSupportActionBar(toolbar);
        setTitle("Activity Title");
    }

    /**
     * Create a new Dagger ObjectGraph to add new dependencies using a plus
     * operation and inject the declared one in the activity. This new graph
     * will be destroyed once the activity lifecycle finish.
     * <p>
     * This is the key of how to use Activity scope dependency injection.
     */
    private void injectDependencies() {
        CampusApplication application = (CampusApplication) getApplication();
        List<Object> modules = getModules();
        modules.add(new ActivityModule(this));
        objectGraph = application.plus(modules);
        inject(this);
    }

    /**
     * Method used to resolve dependencies provided by Dagger modules. Inject
     * an object to provide
     * every @Inject annotation contained.
     *
     * @param object to inject.
     */
    public void inject(Object object) {
        objectGraph.inject(object);
    }

    /**
     * Get a list of Dagger modules with Activity scope needed to this Activity.
     *
     * @return modules with new dependencies to provide.
     */
    protected abstract List<Object> getModules();

    /**
     * Replace every field annotated with ButterKnife annotations like @Bind
     * with the proper
     * value.
     */
    protected void bindViews() {
        ButterKnife.bind(this);
    }

    /**
     * Open or close DrawerLayout when pressed some item of list
     */
    private void initialisemDrawerToggle() {
        /*  mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open_menu,
                R.string.close_menu
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(myDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };*/
    }

    /**
     * Creating items of drawerlayout
     */
    private void initialiseListDrawer() {
        dataList = new ArrayList<DrawerItem>();
        dataList.add(new DrawerItem(R.string.bulletin, R.drawable.logo_ogoloshennya));
        dataList.add(new DrawerItem(R.string.monitoring, R.drawable.logo_monitoring));
        dataList.add(new DrawerItem(R.string.rectorial, R.drawable.logo_rektorskiy));
        dataList.add(new DrawerItem(R.string.vote, R.drawable.logo_golosuvannya));
        dataList.add(new DrawerItem(R.string.npp, R.drawable.logo_navantajenna_npp));
        dataList.add(new DrawerItem(R.string.rnp, R.drawable.logo_rnp));
        dataList.add(new DrawerItem(R.string.settings_drawer, R.drawable.drawer_layout_settings));
        dataList.add(new DrawerItem(R.string.logout, R.drawable.drawer_layout_logout));
    }

    /*
    This code needed for clickable drawer layout list
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id
        ) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    /**
     * Open activity when pressed some item of drawer list
     *
     * @param position
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
       /* Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FirstFragment();
                break;
            case 1:
                fragment = new SecondFragment();
                break;
            case 2:
                fragment = new ThirdFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            myDrawerList.setItemChecked(position, true);
            myDrawerList.setSelection(position);
            setTitle(viewsNames[position]);
            myDrawerLayout.closeDrawer(myDrawerList);

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }*/
    }

}
