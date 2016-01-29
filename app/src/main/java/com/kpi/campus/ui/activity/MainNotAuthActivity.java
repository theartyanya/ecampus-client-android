package com.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kpi.campus.R;
import com.kpi.campus.model.Subsystem;
import com.kpi.campus.model.SubsystemManager;
import com.kpi.campus.ui.adapter.SubsystemAdapter;
import com.kpi.campus.ui.presenter.MainNotAuthPresenter;
import com.kpi.campus.ui.presenter.UIModule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainNotAuthActivity extends BaseActivity implements MainNotAuthPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view_subsystems)
    RecyclerView mRecyclerView;
    @Inject
    MainNotAuthPresenter mPresenter;

    SubsystemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_not_auth);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    protected List<Object> getModules() {
        LinkedList<Object> modules = new LinkedList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_not_auth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                mPresenter.openLogin();
                break;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setViewComponent() {
        setToolbar();
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SubsystemAdapter(this, new ArrayList<Subsystem>());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setData(mPresenter.getSubsystemList());
        mAdapter.setImageArray(mPresenter.getSubsystemImageArray());
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.campus);
        getSupportActionBar().setTitle(R.string.activity_name_main);
    }
}
