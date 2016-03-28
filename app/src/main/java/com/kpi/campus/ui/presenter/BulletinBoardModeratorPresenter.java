package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.kpi.campus.R;
import com.kpi.campus.ui.Navigator;

import javax.inject.Inject;

/**
 * Created by Administrator on 19.02.2016.
 */
public class BulletinBoardModeratorPresenter extends BasePresenter {
    private IView mView;
    private Context mContext;
    private Navigator mNavigator;

    @Inject
    public BulletinBoardModeratorPresenter(Context context, Navigator navigator) {
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

    public TypedArray getTabsIcon() {
        Resources r = mContext.getResources();
        return r.obtainTypedArray(R.array.bulletin_board_moderator_tab_icon);
    }

    public void onButtonAddClick(String title) {
        mNavigator.startNewBulletinActivity(title);
    }



    public interface IView {
        void setViewComponent();
    }
}
