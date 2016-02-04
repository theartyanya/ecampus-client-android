package com.kpi.campus.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.ui.adapter.BulletinPagerAdapter;
import com.kpi.campus.ui.presenter.BulletinBoardPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class BulletinBoardActivity extends BaseActivity implements BulletinBoardPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Inject
    BulletinBoardPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
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

    private void setViewPager() {
        CharSequence[] tabNames = mPresenter.getTabsName();
        BulletinPagerAdapter adapter = new BulletinPagerAdapter(getSupportFragmentManager(), tabNames);
        mViewPager.setAdapter(adapter);
    }

    private void setTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        //setupTabIcon();
    }

    private void setupTabIcon() {
        TypedArray tabIcon = mPresenter.getTabsIcon();
        mTabLayout.getTabAt(BulletinPagerAdapter.BULLETIN_TAB_0)
                .setIcon(tabIcon.getResourceId(BulletinPagerAdapter.BULLETIN_TAB_0, -1));
        mTabLayout.getTabAt(BulletinPagerAdapter.BULLETIN_TAB_1)
                .setIcon(tabIcon.getResourceId(BulletinPagerAdapter.BULLETIN_TAB_1, -1));
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_name_bulletin_board);
    }
}
