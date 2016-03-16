package com.kpi.campus.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kpi.campus.R;
import com.kpi.campus.model.Bulletin;
import com.kpi.campus.ui.adapter.BulletinAdapter;
import com.kpi.campus.ui.presenter.Bb1TabPresenter;
import com.kpi.campus.ui.view.OnItemClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * BbActualTabFragment
 *
 */
public class BbActualTabFragment extends BaseFragment implements Bb1TabPresenter.IView {

    @Bind(R.id.recycler_view_bulletin)
    RecyclerView mRecyclerView;
    @Inject
    Bb1TabPresenter mPresenter;
    BulletinAdapter mAdapter;

    private final boolean IS_MODERATOR = false;

    private OnItemClickListener onItemClickListener =
            new OnItemClickListener() {
                @Override
                public void onItemClicked(View view, int position) {
                    mPresenter.onItemClick(position);
                }
            };

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
        mAdapter = new BulletinAdapter(new ArrayList<Bulletin>(), IS_MODERATOR);
        mAdapter.setData(mPresenter.getData());
        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }
}
