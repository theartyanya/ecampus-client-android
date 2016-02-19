package com.kpi.campus.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.ui.adapter.BulletinTabPagerAdapter;
import com.kpi.campus.ui.presenter.BulletinBoardModeratorPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class BulletinBoardModeratorActivity extends BaseActivity implements BulletinBoardModeratorPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Inject
    BulletinBoardModeratorPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board_moderator);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bulletin_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
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
        setViewPager();
        setTabLayout();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_name_bulletin_board);
    }

    private void setViewPager() {
        CharSequence[] tabNames = mPresenter.getTabsName();
        List<Fragment> fragments = mPresenter.getFragments();
        BulletinTabPagerAdapter adapter = new BulletinTabPagerAdapter(getSupportFragmentManager(), tabNames,fragments);
        mViewPager.setAdapter(adapter);
    }

    private void setTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        //setupTabIcon();
    }

    private void setupTabIcon() {
        TypedArray tabIcon = mPresenter.getTabsIcon();
        mTabLayout.getTabAt(BulletinTabPagerAdapter.BULLETIN_TAB_0)
                .setIcon(tabIcon.getResourceId(BulletinTabPagerAdapter.BULLETIN_TAB_0, -1));
        mTabLayout.getTabAt(BulletinTabPagerAdapter.BULLETIN_TAB_1)
                .setIcon(tabIcon.getResourceId(BulletinTabPagerAdapter.BULLETIN_TAB_1, -1));
    }

    @OnClick(R.id.fab_add)
    public void fabAddOnClick() {
        mPresenter.onButtonAddClick(getString(R.string.add_new_bulletin));
    }

    @OnClick(R.id.fab_other)
    public void fabOtherOnClick() {

    }
}
