package com.kpi.campus.ui.activity;

import android.os.Bundle;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.ui.presenter.EditBulletinPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for edition of a Bulletin.
 * Created by Administrator on 22.04.2016.
 */
public class EditBulletinActivity extends BaseActivity implements
        EditBulletinPresenter.IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_markup);
        bindViews();
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
