package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;

import com.kpi.campus.R;
import com.kpi.campus.ui.Navigator;
import com.kpi.campus.ui.fragment.BbActualTabFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Fragment> getFragments() {
        return new ArrayList<>(Arrays.asList(new BbActualTabFragment()));
//        return new ArrayList<>(Arrays.asList(new BbActualTabFragment(), new BbActualTabFragment(), new BbActualTabFragment()));
    }

    public boolean isModerator() {
        return true;
    }

    public void openBulletinModeratorActivity() {
        mNavigator.startBulletinBoardModeratorActivity();
    }

    public interface IView {
        void setViewComponent();
    }
}
