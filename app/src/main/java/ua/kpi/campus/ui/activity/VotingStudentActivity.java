package ua.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import ua.kpi.campus.R;
import ua.kpi.campus.di.UIModule;
import ua.kpi.campus.model.pojo.Item;
import ua.kpi.campus.model.pojo.VoteTeacher;
import ua.kpi.campus.ui.adapter.ItemSpinnerAdapter;
import ua.kpi.campus.ui.adapter.NothingSelectedAdapter;
import ua.kpi.campus.ui.adapter.VotingAdapter;
import ua.kpi.campus.ui.presenter.VotingStudentPresenter;
import ua.kpi.campus.ui.view.DividerItemDecoration;
import ua.kpi.campus.ui.view.OnItemClickListener;

/**
 * Created by Administrator on 31.05.2016.
 */
public class VotingStudentActivity extends BaseActivity implements
        VotingStudentPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerview_teachers)
    RecyclerView mRecyclerView;
    @Bind(R.id.spinner_terms)
    Spinner mSpinnerTerms;
    @Inject
    VotingStudentPresenter mPresenter;
    private VotingAdapter mAdapter;

    private OnItemClickListener onItemClickListener =
            new OnItemClickListener() {
                @Override
                public void onItemClicked(View view, int position, Object
                        item) {
                    mPresenter.onItemClick(item);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_student);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();

        mPresenter.loadVoting();
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

    @Override
    public void setTermsSpinner(List<Item> list) {
        ArrayAdapter<Item> adapter = new ItemSpinnerAdapter(this, R.layout
                .spinner_item, R.layout.spinner_dropdown_item, list);
        mSpinnerTerms.setAdapter(new NothingSelectedAdapter(
                adapter,
                R.layout.spinner_nothing_selected_terms,
                this));
        mSpinnerTerms.setOnItemSelectedListener(new AdapterView
                .OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                if (item != null) {
                    if (mAdapter == null)
                        mPresenter.setSpecificAdapter();
                    mAdapter.filterByTerm(item.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // N/A
            }
        });
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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setSaveEnabled(true);
    }

    private void setVotingAdapter(List<VoteTeacher> teachers) {
        mAdapter = new VotingAdapter();
        mAdapter.setAllItems(teachers);
        mAdapter.setHasStableIds(true);
        mAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }
}
