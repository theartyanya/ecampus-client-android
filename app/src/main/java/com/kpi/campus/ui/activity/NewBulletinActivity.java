package com.kpi.campus.ui.activity;

import android.os.Bundle;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.ui.presenter.MainPresenter;
import com.kpi.campus.ui.presenter.NewBulletinPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewBulletinActivity extends BaseActivity  implements NewBulletinPresenter.IView{

    @Inject
    NewBulletinPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bulletin);
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

    }
}
