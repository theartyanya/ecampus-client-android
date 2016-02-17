package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.kpi.campus.R;
import com.kpi.campus.ui.Navigator;

import javax.inject.Inject;

/**
 * BulletinBoardPresenter created to manage BulletinBoardActivity.
 *
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

    public CharSequence[] getTabsName() {
        Resources r = mContext.getResources();
        return r.getStringArray(R.array.bulletin_board_tab);
    }

    public TypedArray getTabsIcon() {
        Resources r = mContext.getResources();
        return r.obtainTypedArray(R.array.bulletin_board_tab_icon);
    }

    public void onButtonAddClick() {
        mNavigator.startNewBulletinActivity();
    }

    public interface IView {
        void setViewComponent();
    }
}
