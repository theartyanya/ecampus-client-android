package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.kpi.campus.R;
import com.kpi.campus.model.pojo.Bulletin;
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

    /**
     * Get array of icons of BulletinBoardModerator tabs.
     * @return typed array of icons
     */
    public TypedArray getTabsIcon() {
        Resources r = mContext.getResources();
        return r.obtainTypedArray(R.array.bulletin_board_moderator_tab_icon);
    }

    /**
     * Handles click on Add button.
     * Open NewBulletinActivity class
     * @param title title of activity to be displayed on toolbar.
     */
    public void onButtonAddClick(String title) {
        mNavigator.startNewBulletinActivity(title);
    }

    /**
     * Handles click on recyclerview item.
     * @param item
     */
    public void onItemClick(Object item) {
        mNavigator.startBulletinContentActivity((Bulletin) item);
    }

    public interface IView {
        void setViewComponent();
    }
}
