package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;

import com.kpi.campus.R;
import com.kpi.campus.ui.Navigator;
import com.kpi.campus.ui.fragment.Bb3TabFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public CharSequence[] getTabsName() {
        Resources r = mContext.getResources();
        return r.getStringArray(R.array.bulletin_board_moderator_tab);
    }

    public TypedArray getTabsIcon() {
        Resources r = mContext.getResources();
        return r.obtainTypedArray(R.array.bulletin_board_tab_icon);
    }

    public List<Fragment> getFragments() {
        return new ArrayList<Fragment>(Arrays.asList(new Bb3TabFragment(), new Bb3TabFragment(), new Bb3TabFragment(), new Bb3TabFragment(), new Bb3TabFragment()));
    }

    public void onButtonAddClick(String title) {
        mNavigator.startNewBulletinActivity(title);
    }

    public interface IView {
        void setViewComponent();
    }
}
