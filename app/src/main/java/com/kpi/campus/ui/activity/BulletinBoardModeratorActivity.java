package com.kpi.campus.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.dao.IDataAccessObject;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.rx.BulletinModeratorResponseManager;
import com.kpi.campus.ui.adapter.PagingRecyclerAdapter;
import com.kpi.campus.ui.presenter.BulletinBoardModeratorPresenter;
import com.kpi.campus.ui.view.OnItemClickListener;
import com.kpi.campus.util.pagination.PaginationTool;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class BulletinBoardModeratorActivity extends BaseActivity implements BulletinBoardModeratorPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.recycler_view_bulletin)
    RecyclerView mRecyclerView;
    @Inject
    BulletinBoardModeratorPresenter mPresenter;
    private PagingRecyclerAdapter mAdapter;
    private Subscription mPagingSubscription;
    /**
     * Is this activity is moderator activity.
     * Always true. Affects on recycler item view (enable Edit button).
     */
    private final boolean IS_MODERATOR_MODE = true;
    /**
     * Tabs indices
     */
    private final int TAB_ALL = 0;
    private final int TAB_ACTUAL = 1;
    private final int TAB_PROFILE = 2;
    private final int TAB_SUBDIVISION = 3;
    private final int TAB_DELETED = 4;
    /**
     * Limit for the number of loaded items
     */
    private final static int LIMIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board_moderator);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    private OnItemClickListener onItemClickListener =
            new OnItemClickListener() {
                @Override
                public void onItemClicked(View view, int position, Object item) {
                    mPresenter.onItemClick(item);
                }
            };

    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.edit:
                    mPresenter.editBulletin(getString(R.string.edit_new_bulletin));
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bulletin_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
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

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_bulletin_moderator_mode_title);
    }

    private void setTabLayout() {
        TypedArray tabIcon = mPresenter.getTabsIcon();
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(TAB_ALL, -1)), true);
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(TAB_ACTUAL, -1)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(TAB_PROFILE, -1)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(TAB_SUBDIVISION, -1)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(tabIcon.getResourceId(TAB_DELETED, -1)));
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
        List<Bulletin> bulletins = new ArrayList<>();
        switch (tabPosition) {
            case TAB_ALL:
                break;
            case TAB_ACTUAL:

                break;
            case TAB_PROFILE:

                break;
            case TAB_SUBDIVISION:
                break;
            case TAB_DELETED:
                break;
            default:
                mAdapter.setItems(bulletins);
        }
    }

    @OnClick(R.id.fab_add)
    public void fabAddOnClick() {
        mPresenter.onButtonAddClick(getString(R.string.add_new_bulletin));
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
        PaginationTool<List<Bulletin>> paginationTool = PaginationTool.buildPagingObservable(mRecyclerView, lastId -> new BulletinModeratorResponseManager().getResponse(lastId, LIMIT))
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
}
