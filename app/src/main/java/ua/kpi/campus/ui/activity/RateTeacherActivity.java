package ua.kpi.campus.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import ua.kpi.campus.Config;
import ua.kpi.campus.R;
import ua.kpi.campus.di.UIModule;
import ua.kpi.campus.model.Rating;
import ua.kpi.campus.model.pojo.VoteTeacher;
import ua.kpi.campus.ui.adapter.RateAdapter;
import ua.kpi.campus.util.SnackbarUtil;

/**
 * Created by Administrator on 08.06.2016.
 */
public class RateTeacherActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.listview_rate)
    ListView mList;
    private RateAdapter mAdapter;
    private VoteTeacher mTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_teacher);
        mTeacher = getIntent().getParcelableExtra(Config.KEY_TEACHER);
        bindViews();
        setToolbar();
        setListView();
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rate_teacher, menu);
        if(mTeacher.isVoted()) {
            final MenuItem item = menu.findItem(R.id.action_done);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_done:
                if (mAdapter.teacherIsRated()) {
                    saveRating();
                } else
                    SnackbarUtil.show(getString(R.string.rate_by_all_criteria),
                            findViewById(R.id.root_layout));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Rating> setRatingList() {
        List<Rating> list = new ArrayList<>();
        String[] criterion = getResources().getStringArray(R.array
                .voting_criteria);
        for (int i = 0; i < criterion.length; i++)
            list.add(new Rating(0, criterion[i]));
        return list;
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.activity_name_rate_teacher);
    }

    private void setListView() {
        mAdapter = new RateAdapter(this, R.layout.list_rating_item,
                setRatingList());
        mList.setAdapter(mAdapter);
    }

    private void saveRating() {
        // send to server
        List<Rating> rating = mAdapter.getData();
        mTeacher.setCriteria(rating);
        mTeacher.setIsVoted(true);

        Intent intent = new Intent();
        intent.putExtra(Config.KEY_TEACHER, mTeacher);
        setResult(RESULT_OK, intent);
        finish();
    }
}
