package com.kpi.campus.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.Bulletin;
import com.kpi.campus.model.dao.IDataAccessObject;
import com.kpi.campus.rx.BulletinResponseManager;
import com.kpi.campus.ui.adapter.PagingRecyclerAdapter;
import com.kpi.campus.ui.presenter.BulletinBoardPresenter;
import com.kpi.campus.ui.view.OnItemClickListener;
import com.kpi.campus.util.pagination.PaginationTool;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Bulletin board activity.
 */
public class BulletinBoardActivity extends BaseActivity implements BulletinBoardPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.recycler_view_bulletin)
    RecyclerView mRecyclerView;
    @Inject
    BulletinBoardPresenter mPresenter;
    PagingRecyclerAdapter mAdapter;
    private Subscription mPagingSubscription;

    /**
     * Whether the user is moderator.
     * Affects on toolbar menu view.
     */
    private boolean mIsModerator;
    /**
     * Is this activity is moderator activity.
     * Always false. Affects on recycler item view (disable Edit button).
     */
    private final boolean IS_MODERATOR_MODE = false;
    /**
     * Tabs indices
     */
    private final int TAB_ACTUAL = 0;
    private final int TAB_PROFILE = 1;
    private final int TAB_SUBDIVISION = 2;
    /**
     * Limit for the number of loaded items
     */
    private final static int LIMIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();

        mIsModerator = mPresenter.isModerator();
    }

    private OnItemClickListener onItemClickListener =
            new OnItemClickListener() {
                @Override
                public void onItemClicked(View view, int position, Object item) {
                    mPresenter.onItemClick(item);
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mIsModerator) {
            getMenuInflater().inflate(R.menu.menu_bulletin_board_moderator, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_bulletin_board, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_moderator:
                mPresenter.openBulletinModeratorActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public void setViewComponent() {
        setToolbar();
        setTabLayout();
        setRecyclerView();
    }

    private void setTabLayout() {
        TypedArray tabIcon = mPresenter.getTabsIcon();
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(TAB_ACTUAL, -1)), true);
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(TAB_PROFILE, -1)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(TAB_SUBDIVISION, -1)));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // N/A
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // N/A
            }
        });
    }

    private void setCurrentTabFragment(int tabPosition) {
        switch (tabPosition) {
            case TAB_ACTUAL:
//                List<Bulletin> bulletins = mPresenter.getData();
//                mAdapter.addData(bulletins);
                break;
            case TAB_PROFILE:
                //mAdapter.setData(list2);
                break;
            case TAB_SUBDIVISION:
                //mAdapter.setData(list3);
                break;
        }
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_name_bulletin_board);
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new PagingRecyclerAdapter(IS_MODERATOR_MODE);
        mAdapter.setHasStableIds(true);

        mRecyclerView.setSaveEnabled(true);

        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

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
        PaginationTool<List<Bulletin>> paginationTool = PaginationTool.buildPagingObservable(mRecyclerView, offset -> BulletinResponseManager.getInstance().getResponse(offset, LIMIT))
                .setLimit(LIMIT)
                .build();

        mPagingSubscription = paginationTool
                .getPagingObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bulletin>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Bulletin> items) {
                        IDataAccessObject<Bulletin> dao = mPresenter.getDao();
                        dao.setData(items);

                        mAdapter.addNewItems(items);
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mPagingSubscription != null && !mPagingSubscription.isUnsubscribed()) {
            mPagingSubscription.unsubscribe();
        }
        // for memory leak prevention (RecycleView is not unsubscibed from adapter DataObserver)
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(null);
        }
        super.onDestroy();
    }

    @VisibleForTesting
    public boolean userIsModerator() {
        return mIsModerator;
    }
}
