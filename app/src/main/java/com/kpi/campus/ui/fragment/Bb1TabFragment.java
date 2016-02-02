package com.kpi.campus.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpi.campus.R;
import com.kpi.campus.ui.presenter.Bb1TabPresenter;

import javax.inject.Inject;

/**
 */
public class Bb1TabFragment extends BaseFragment implements Bb1TabPresenter.IView {

    @Inject
    Bb1TabPresenter mPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_bulletin_1;
    }

    @Override
    public void setViewComponent() {

    }
}
