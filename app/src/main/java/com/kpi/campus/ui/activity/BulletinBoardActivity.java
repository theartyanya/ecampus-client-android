package com.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.ui.presenter.BulletinBoardPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class BulletinBoardActivity extends BaseActivity implements BulletinBoardPresenter.IView{

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Inject
    BulletinBoardPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_name_bulletin_board);
    }
}
