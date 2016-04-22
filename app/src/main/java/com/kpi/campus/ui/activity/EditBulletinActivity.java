package com.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kpi.campus.Config;
import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.ui.presenter.EditBulletinPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Activity for edition of a Bulletin.
 * Created by Administrator on 22.04.2016.
 */
public class EditBulletinActivity extends BaseActivity implements
        EditBulletinPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    EditBulletinPresenter mPresenter;

    private Bulletin mCurrentBulletin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_markup);
        bindViews();
        mCurrentBulletin = getIntent().getParcelableExtra(Config.KEY_BULLETIN);
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
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.add_new_bulletin);
    }
}
