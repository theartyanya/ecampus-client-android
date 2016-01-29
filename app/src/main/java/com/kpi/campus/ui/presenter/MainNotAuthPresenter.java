package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.kpi.campus.R;
import com.kpi.campus.model.Subsystem;
import com.kpi.campus.model.SubsystemManager;
import com.kpi.campus.ui.Navigator;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 28.01.2016.
 */
public class MainNotAuthPresenter extends BasePresenter {

    private IView mView;
    private Context mContext;
    private Navigator mNavigator;

    @Inject
    public MainNotAuthPresenter(Context context, Navigator navigator) {
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

    public List<Subsystem> getSubsystemList() {
        Resources r = mContext.getResources();
        String[] s = r.getStringArray(R.array.partial_subsystem);
        return SubsystemManager.getInstance().getSubsystems(s);
    }

    public TypedArray getSubsystemImageArray() {
        return mContext.getResources().obtainTypedArray(R.array.partial_subsystem_image);
    }

    public void openLogin() {
        mNavigator.startLoginActivity();
    }

    public interface IView {
        void setViewComponent();
    }
}
