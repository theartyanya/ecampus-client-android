package ua.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import ua.kpi.campus.CampusApplication;
import ua.kpi.campus.R;
import ua.kpi.campus.di.ActivityModule;
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


    ////////////////////////////////////////////////////////////////
    private ListView myDrawerList;
    private ActionBarDrawerToggle myDrawerToggle;

    // navigation drawer title
    private CharSequence myDrawerTitle;
    // used to store app title
    private CharSequence myTitle;
    private List<DrawerItem> dataList;
    private String[] viewsNames;

    ///////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(int layoutResID) {
        dataList = new ArrayList<DrawerItem>();
        dataList.add(new DrawerItem("Message", R.drawable.cat_lev));
        dataList.add(new DrawerItem("Likes", R.drawable.logo_lev));
        dataList.add(new DrawerItem("Games", R.drawable.logo_lev));
        // dataList.add(new DrawerItem("Lables", R.drawable.logo_lev));
        //  dataList.add(new DrawerItem("Search", R.drawable.ic_action_search));
        ////////////////////////////////
        myTitle = getTitle();
        myDrawerTitle = getResources().getString(R.string.menu);
        viewsNames = getResources().getStringArray(ua.kpi.campus.R.array.views_array);

        ///////////////////////////////////////////////////////////////////////////////
        DrawerLayout myDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(ua.kpi.campus.R.layout.drawer_layout, null);
        FrameLayout activityContainer = (FrameLayout) myDrawerLayout.findViewById(ua.kpi.campus.R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        ////////////////|||||||||||||||||||||||||||||||||||||||||||||||||||///////////////////////
        myDrawerList = (ListView) myDrawerLayout.findViewById(R.id.list_left_drawer);

        myDrawerList.setAdapter(new CustomDrawerAdapter(this,
                ua.kpi.campus.R.layout.drawer_layout_list_item_2, dataList));

      /*  myDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout,
                R.string.open_menu,
                R.string.close_menu
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(myTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(myDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        myDrawerLayout.setDrawerListener(myDrawerToggle);

        myDrawerList.setOnItemClickListener(new DrawerItemClickListener());*/
        ///|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||////////////////////////////
        super.setContentView(myDrawerLayout);
        //Toolbar toolbar = (Toolbar) findViewById(ua.kpi.campus.R.id.toolbar);
        // setSupportActionBar(toolbar);
        setTitle("Activity Title");
        ///////////////////////////////////////////////////////////////////////////
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


    /////////////////////////////////////////////////////////////////////////////////
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id
        ) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

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
    /////////////////////////////////////////////////////////////////////////////////

}
