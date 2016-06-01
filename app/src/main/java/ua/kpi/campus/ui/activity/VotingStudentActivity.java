package ua.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import ua.kpi.campus.R;
import ua.kpi.campus.di.UIModule;
import ua.kpi.campus.model.pojo.VoteTeacher;
import ua.kpi.campus.ui.adapter.VotingAdapter;
import ua.kpi.campus.ui.presenter.VotingStudentPresenter;
import ua.kpi.campus.ui.view.ExtendedRecyclerView;

/**
 * Created by Administrator on 31.05.2016.
 */
public class VotingStudentActivity extends BaseActivity implements VotingStudentPresenter.IView{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_teachers)
    ExtendedRecyclerView mRecyclerView;
    @Inject
    VotingStudentPresenter mPresenter;
    private VotingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_student);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
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
        setRecyclerView();
    }

    @Override
    public void setVoteInProgressAdapter(List<VoteTeacher> teachers) {
        setVotingAdapter(teachers);
    }

    @Override
    public void setVoteEndedAdapter(List<VoteTeacher> teachers) {

    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_name_voting);
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager
                (getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setSaveEnabled(true);
    }

    private void setVotingAdapter(List<VoteTeacher> teachers) {
        mAdapter = new VotingAdapter();
        mAdapter.setItems(teachers);
        mAdapter.setHasStableIds(true);
        //mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }
}
