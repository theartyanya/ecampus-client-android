package com.kpi.campus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.kpi.campus.R;
import com.kpi.campus.model.Subsystem;
import com.kpi.campus.model.SubsystemManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 28.01.2016.
 */
public class MainNotAuthPresenter extends BasePresenter {

    private IView mView;
    private Context mContext;

    @Inject
    public MainNotAuthPresenter(Context context) {
        mContext = context;
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

    public interface IView {
        void setViewComponent();
    }
}
