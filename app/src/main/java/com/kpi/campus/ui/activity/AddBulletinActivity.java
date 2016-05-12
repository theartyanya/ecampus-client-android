package com.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.Recipient;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.User;
import com.kpi.campus.ui.presenter.SaveBulletinPresenter;
import com.kpi.campus.util.DateUtil;
import com.kpi.campus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for addition of a Bulletin.
 */
public class AddBulletinActivity extends SaveBulletinActivity implements
        SaveBulletinPresenter.IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_markup);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(START_DATE, mStartDate.getText()
                .toString());
        savedInstanceState.putString(END_DATE, mEndDate.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mStartDate.setText(savedInstanceState.getString(START_DATE));
        mEndDate.setText(savedInstanceState.getString(END_DATE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_bulletin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_clear:
                clearValues();
                ToastUtil.showShortMessage(getString(R.string.clear), this);
                break;
            case R.id.action_done:
                if (isValidInput())
                    mPresenter.onStartRequest(() -> mPresenter.addBulletin());
                break;
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
        setDateListener();
        setAdapter();
        setRadioGroup();
        setInitialViewValues();
    }

    @Override
    public void showResponse(int code, String msg) {
        switch (code) {
            case 200:
                ToastUtil.showShortMessage(getString(R.string
                        .bulletin_is_added), this);
                break;
            case 400:
                ToastUtil.showShortMessage(getString(R.string.bad_bulletin),
                        this);
                break;
            case 401:
                ToastUtil.showShortMessage(getString(R.string.unauthorized),
                        this);
                break;
            case 500:
                ToastUtil.showShortMessage(getString(R.string.server_error),
                        this);
                break;
            default:
                break;
        }
    }

    @Override
    public Bulletin formBulletin() {
        String userId;
        userId = User.getInstance().id;
        List<Recipient> r = mAdapter.getItems();
        Bulletin bulletin = new Bulletin(userId, mSubject.getText().toString
                (), mText.getText().toString(), mCreateDate.getText()
                .toString(), mStartDate.getText().toString(), mEndDate
                .getText().toString(), true, r);
        return bulletin;
    }

    @Override
    public String getBulletinId() {
        return null;
    }

    @Override
    public void setRecipientsList(List<Recipient> list) {
        // N / A
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.add_bulletin);
    }

    private void setInitialViewValues() {
        User user = User.getInstance();
        TextView tv = (TextView) findViewById(R.id.text_view_author_name);
        tv.setText(user.name);
        tv = (TextView) findViewById(R.id.text_view_actuality_value);
        tv.setText(R.string.yes);
        String currentDate = DateUtil.getCurrentDate(DateUtil.FORMAT);
        mCreateDate.setText(currentDate);
        tv = (TextView) findViewById(R.id
                .text_view_change_actuality_date_value);
        tv.setText(currentDate);
    }
}