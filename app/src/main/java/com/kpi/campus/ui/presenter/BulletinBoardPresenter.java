package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.kpi.campus.R;
import com.kpi.campus.model.Bulletin;
import com.kpi.campus.model.dao.BulletinDao;
import com.kpi.campus.model.dao.IDataAccessObject;
import com.kpi.campus.rx.BulletinRxLoader;
import com.kpi.campus.ui.Navigator;

import java.util.List;

import javax.inject.Inject;

/**
 * BulletinBoardPresenter created to manage BulletinBoardActivity.
 *
 * Created by Admin on 02.02.2016.
 */
public class BulletinBoardPresenter extends BasePresenter {

    public static boolean IS_LOADING;

    private IView mView;
    private Context mContext;
    private Navigator mNavigator;
    private IDataAccessObject<Bulletin> mDataAccess;

    @Inject
    public BulletinBoardPresenter(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mDataAccess = new BulletinDao();
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    /**
     * Get icons of BulletinBoard tabs.
     * @return array of resources
     */
    public TypedArray getTabsIcon() {
        Resources r = mContext.getResources();
        return r.obtainTypedArray(R.array.bulletin_board_tab_icon);
    }

    /**
     * Return whether current user is Moderator of BulletinBoard
     * @return true if user is moderator, else otherwise
     */
    public boolean isModerator() {
        return true;
    }

    /**
     * Start BulletinBoardModeratorActivity
     */
    public void openBulletinModeratorActivity() {
        mNavigator.startBulletinBoardModeratorActivity();
    }

    /**
     * Handles click on recyclerview item.
     * @param position
     */
    public void onItemClick(int position) {
        mNavigator.startBulletinContentActivity(position);
    }

    /**
     * Load necessary data from REST API
     */
    public void loadData() {
        if(!IS_LOADING){
            IS_LOADING = true;
            BulletinRxLoader load = new BulletinRxLoader(mDataAccess);
            load.apiCall();
        }
    }

    /**
     * Get Bulletin data from DataAccessObject
     * @return list of bulletins
     */
    public List<Bulletin> getData() {
        return mDataAccess.getData();
    }

    public IDataAccessObject getDao() {
        return mDataAccess;
    }

    public interface IView {
        void setViewComponent();
    }
}
