package com.kpi.campus.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kpi.campus.R;
import com.kpi.campus.model.Bulletin;
import com.kpi.campus.ui.adapter.BulletinAdapter;
import com.kpi.campus.ui.presenter.BbActualTabPresenter;
import com.kpi.campus.ui.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscription;

/**
 * BbActualTabFragment
 *
 */
public class BbActualTabFragment extends BaseFragment implements BbActualTabPresenter.IView {

    @Bind(R.id.recycler_view_bulletin)
    RecyclerView mRecyclerView;
    @Inject
    BbActualTabPresenter mPresenter;
    BulletinAdapter mAdapter;
    private Subscription mPagingSubscription;

    private final boolean IS_MODERATOR_MODE = false;

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

        setRetainInstance(true);
        setRecyclerView(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_bulletin_actual;
    }


    private void setRecyclerView(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);

        if (savedInstanceState == null) {
            mAdapter = new BulletinAdapter(new ArrayList<Bulletin>(), IS_MODERATOR_MODE);
            mAdapter.setHasStableIds(true);
        }
        mRecyclerView.setSaveEnabled(true);

        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

        List<Bulletin> data = mPresenter.getData();
        mAdapter.setData(data);
        // if all items was loaded we don't need Pagination
        if (mAdapter.isAllItemsLoaded()) {
            return;
        }

        setRecyclerViewPagination();
    }

    /**
     * RecyclerView pagination
     */
    private void setRecyclerViewPagination() {

    }
}
