package com.kpi.campus.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.Bulletin;
import com.kpi.campus.model.dao.BulletinDao;
import com.kpi.campus.rx.BulletinRxLoader;
import com.kpi.campus.ui.adapter.BulletinAdapter;
import com.kpi.campus.ui.adapter.BulletinTabPagerAdapter;
import com.kpi.campus.ui.presenter.BulletinBoardPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Bulletin board activity.
 */
public class BulletinBoardActivity extends BaseActivity implements BulletinBoardPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.recycler_view_bulletin)
    RecyclerView mRecyclerView;
    @Inject
    BulletinBoardPresenter mPresenter;
    BulletinAdapter mAdapter;

    private boolean mIsModerator;
    private final boolean IS_MODERATOR_MODE = false;

    List<Bulletin> list1;
    List<Bulletin> list2;
    List<Bulletin> list3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();

        mIsModerator = mPresenter.isModerator();

        list1 = new ArrayList<>();
        list1.add(new Bulletin("theme 1", "author", "date"));

        list2 = new ArrayList<>();
        list2.add(new Bulletin("theme 2", "author", "date"));

        list3 = new ArrayList<>();
        list3.add(new Bulletin("theme 3", "author", "date"));

        BulletinRxLoader load = new BulletinRxLoader(new BulletinDao());
        load.apiCall();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mIsModerator) {
            getMenuInflater().inflate(R.menu.menu_bulletin_board_moderator, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_bulletin_board, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_moderator:
                mPresenter.openBulletinModeratorActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public void setViewComponent() {
        setToolbar();
        //setViewPager();
        setTabLayout();
        setRecyclerView();
    }

    private void setViewPager() {
        CharSequence[] tabNames = mPresenter.getTabsName();
        List<Fragment> fragments = mPresenter.getFragments();
        BulletinTabPagerAdapter adapter = new BulletinTabPagerAdapter(getSupportFragmentManager(), tabNames);
        //mViewPager.setAdapter(adapter);
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new BulletinAdapter(new ArrayList<Bulletin>(), IS_MODERATOR_MODE);
        mAdapter.setHasStableIds(true);

        mRecyclerView.setSaveEnabled(true);

        //mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

        List<Bulletin> data = new ArrayList<>();
        data.add(new Bulletin("theme 0", "author", "date"));
        mAdapter.setData(data);
        // if all items was loaded we don't need Pagination
        if (mAdapter.isAllItemsLoaded()) {
            return;
        }
    }

    private void setTabLayout() {
        TypedArray tabIcon = mPresenter.getTabsIcon();
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(0, -1)), true);
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(1, -1)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(2, -1)));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // N/A
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // N/A
            }
        });

//        mTabLayout.setupWithViewPager(mViewPager);
//        setupTabIcon();
    }

    private void setCurrentTabFragment(int tabPosition) {
        switch (tabPosition) {
            case 0:
                mAdapter.setData(list1);
                break;
            case 1:
                mAdapter.setData(list2);
                break;
            case 2:
                mAdapter.setData(list3);
                break;
        }
    }

    private void setupTabIcon() {
        TypedArray tabIcon = mPresenter.getTabsIcon();
        mTabLayout.getTabAt(BulletinTabPagerAdapter.BULLETIN_TAB_0)
                .setIcon(tabIcon.getResourceId(BulletinTabPagerAdapter.BULLETIN_TAB_0, -1));
        mTabLayout.getTabAt(BulletinTabPagerAdapter.BULLETIN_TAB_1)
                .setIcon(tabIcon.getResourceId(BulletinTabPagerAdapter.BULLETIN_TAB_1, -1));
        mTabLayout.getTabAt(BulletinTabPagerAdapter.BULLETIN_TAB_2)
                .setIcon(tabIcon.getResourceId(BulletinTabPagerAdapter.BULLETIN_TAB_2, -1));
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_name_bulletin_board);
    }

    @VisibleForTesting
    public boolean userIsModerator() {
        return mIsModerator;
    }
}
