package ua.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import ua.kpi.campus.Config;
import ua.kpi.campus.di.UIModule;
import ua.kpi.campus.model.pojo.Bulletin;
import ua.kpi.campus.ui.presenter.BulletinContentPresenter;

/**
 * This activity represents content of a Bulletin.
 */
public class BulletinContentActivity extends BaseActivity implements
        BulletinContentPresenter.IView {
    @Bind(ua.kpi.campus.R.id.toolbar)
    Toolbar mToolbar;
    @Inject
    BulletinContentPresenter mPresenter;
    Bulletin mBulletin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ua.kpi.campus.R.layout.activity_bulletin_content);
        bindViews();
        mBulletin = getIntent().getParcelableExtra(Config.KEY_BULLETIN);
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
        setValuesInViews();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(ua.kpi.campus.R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(ua.kpi.campus.R.string.bulletin_text_content);
    }

    private void setValuesInViews() {
        if (mBulletin == null)
            return;
        TextView textView;
        textView = (TextView) findViewById(ua.kpi.campus.R.id.text_view_bulletin_theme);
        textView.setText(mBulletin.getSubject());

        textView = (TextView) findViewById(ua.kpi.campus.R.id.text_view_bulletin_text);
        textView.setText(mBulletin.getText());

        textView = (TextView) findViewById(ua.kpi.campus.R.id.text_view_bulletin_start_date);
        textView.setText(mBulletin.getDateStart());

        textView = (TextView) findViewById(ua.kpi.campus.R.id.text_view_bulletin_end_date);
        textView.setText(mBulletin.getDateStop());

        textView = (TextView) findViewById(ua.kpi.campus.R.id.text_view_bulletin_author);
        textView.setText(mBulletin.getCreatorName());
    }
}
