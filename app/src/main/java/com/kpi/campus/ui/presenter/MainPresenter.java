package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.kpi.campus.R;
import com.kpi.campus.model.Subsystem;
import com.kpi.campus.ui.Navigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * MainPresenter created to manage MainActivity.
 *
 * Created by Administrator on 01.02.2016.
 */
public class MainPresenter extends BasePresenter {

    private IView mView;
    private Context mContext;
    private Navigator mNavigator;

    @Inject
    public MainPresenter(Context context, Navigator navigator) {
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

    public void startActivityBasedOn(int position) {
        switch (position) {
            case 0:
                mNavigator.startBulletinBoardActivity();
                break;
            case 1:
                break;
        }
    }

    public List<Subsystem> getData() {
        List<Subsystem> subsystems = new ArrayList<>();

        Resources res = getResources();
        String[] names = getSubsystemNames(res);
        TypedArray icons = getSubsystemIcon(res);
        for (int i = 0; i < names.length && i < icons.length(); i++) {
            Subsystem s = new Subsystem(names[i], icons.getResourceId(i, -1));
            subsystems.add(s);
        }
        return subsystems;
    }

    private String[] getSubsystemNames(Resources res) {
        return res.getStringArray(R.array.full_subsystem);
    }

    private TypedArray getSubsystemIcon(Resources res) {
        return res.obtainTypedArray(R.array.full_subsystem_image);
    }

    private Resources getResources() {
        return mContext.getResources();
    }

    public interface IView {
        void setViewComponent();
    }
}
