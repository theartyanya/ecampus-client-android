package com.kpi.campus.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpi.campus.R;
import com.kpi.campus.model.Bulletin;
import com.kpi.campus.ui.adapter.BulletinAdapter;
import com.kpi.campus.ui.presenter.Bb1TabPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;

/**
 */
public class Bb1TabFragment extends BaseFragment implements Bb1TabPresenter.IView {

    @Bind(R.id.recycler_view_bulletin)
    RecyclerView mRecyclerView;
    @Inject
    Bb1TabPresenter mPresenter;
BulletinAdapter mAdapter;

    public static Bb1TabFragment createInstance(int itemsCount) {
        Bb1TabFragment partThreeFragment = new Bb1TabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ITEMS_COUNT_KEY", itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

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
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new BulletinAdapter(new ArrayList<Bulletin>());
        mAdapter.setData(mPresenter.getData());
        mRecyclerView.setAdapter(mAdapter);
    }
}
