package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.kpi.campus.R;
import com.kpi.campus.model.dao.BulletinModeratorDao;
import com.kpi.campus.model.dao.IDataAccessObject;
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
    private IDataAccessObject<Bulletin> mDataAccess;

    @Inject
    public BulletinBoardModeratorPresenter(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mDataAccess = new BulletinModeratorDao();
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
     * @param item an item that is clicked on
     */
    public void onItemClick(Object item) {
        mNavigator.startBulletinContentActivity((Bulletin) item);
    }

    /**
     * Return access object to the list of Bulletins
     * @return access object
     */
    public IDataAccessObject getDao() {
        return mDataAccess;
    }

    public interface IView {
        void setViewComponent();
    }
}
