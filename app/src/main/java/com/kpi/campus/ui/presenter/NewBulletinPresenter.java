package com.kpi.campus.ui.presenter;

import android.content.Context;

import com.kpi.campus.ui.Navigator;

import javax.inject.Inject;

/**
 * Created by Admin on 12.02.2016.
 */
public class NewBulletinPresenter extends BasePresenter {

    private IView mView;

    @Inject
    public NewBulletinPresenter() {
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public interface IView {
        void setViewComponent();
    }
}
