package ua.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import ua.kpi.campus.R;
import ua.kpi.campus.di.UIModule;
import ua.kpi.campus.model.Rating;
import ua.kpi.campus.ui.adapter.RateAdapter;

/**
 * Created by Administrator on 08.06.2016.
 */
public class RateTeacherActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.listview_rate)
    ListView mList;
    private RateAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_teacher);
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
        mAdapter = new RateAdapter(this, R.layout.list_rating_item, setRatingList());
        mList.setAdapter(mAdapter);
    }
}
