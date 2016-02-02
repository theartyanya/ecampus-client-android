package com.kpi.campus.ui.presenter;

import android.content.Context;

import com.kpi.campus.ui.Navigator;

import javax.inject.Inject;

/**
 * Created by Admin on 02.02.2016.
 */
public class BulletinBoardPresenter extends BasePresenter {

    private IView mView;
    private Context mContext;
    private Navigator mNavigator;

    @Inject
    public BulletinBoardPresenter(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
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
