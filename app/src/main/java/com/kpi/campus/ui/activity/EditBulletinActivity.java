package com.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kpi.campus.Config;
import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.ui.fragment.DatePickerFragment;
import com.kpi.campus.ui.presenter.EditBulletinPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Activity for edition of a Bulletin.
 * Created by Administrator on 22.04.2016.
 */
public class EditBulletinActivity extends BaseActivity implements
        EditBulletinPresenter.IView {

    @Bind(R.id.edit_text_bulletin_theme)
    EditText mSubject;
    @Bind(R.id.edit_text_bulletin_text)
    EditText mText;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.text_view_start_period)
    TextView mStartDate;
    @Bind(R.id.text_view_end_period)
    TextView mEndDate;
    @Bind(R.id.text_view_creation_date_value)
    TextView mCreateDate;
    @Bind(R.id.spinner_profile)
    Spinner mSpinnerProfile;
    @Bind(R.id.spinner_group)
    Spinner mSpinnerGroup;
    @Bind(R.id.spinner_subdivision)
    Spinner mSpinnerSubdivision;
    @Bind(R.id.layout_profile)
    RelativeLayout mLayoutProfile;
    @Bind(R.id.layout_group)
    RelativeLayout mLayoutGroup;
    @Bind(R.id.rb_all)
    RadioButton mRbAll;
    @Bind(R.id.rb_profile)
    RadioButton mRbProfile;
    @Bind(R.id.rb_group)
    RadioButton mRbGroup;
    @Inject
    EditBulletinPresenter mPresenter;

    private Bulletin mCurrentBulletin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_markup);
        bindViews();
        mCurrentBulletin = getIntent().getParcelableExtra(Config.KEY_BULLETIN);
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_bulletin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_delete:
                break;
            case R.id.action_clear:
                break;
            case R.id.action_done:
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
        setRadioGroup();
        setDateListener();
        setInitialViewValues();
    }

    private void setInitialViewValues() {
        if (mCurrentBulletin == null)
            return;
        mSubject.setText(mCurrentBulletin.getSubject());
        mText.setText(mCurrentBulletin.getText());
        mStartDate.setText(mCurrentBulletin.getDateStart());
        mEndDate.setText(mCurrentBulletin.getDateEnd());
        mCreateDate.setText(mCurrentBulletin.getDateCreate());

        TextView tv = (TextView) findViewById(R.id.text_view_actuality_value);
        if (mCurrentBulletin.getActuality())
            tv.setText(R.string.yes);
        else
            tv.setText(R.string.no);
        tv = (TextView) findViewById(R.id
                .text_view_change_actuality_date_value);
        tv.setText(mCurrentBulletin.getDateCreate());
    }

    private void setDateListener() {
        mStartDate.setOnClickListener(v -> setDateTo(mStartDate, "2"));
        mEndDate.setOnClickListener(v -> setDateTo(mEndDate, "1"));
    }

    private void setDateTo(TextView textView, String uniqueString) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setTextView(textView);
        newFragment.show(getFragmentManager(), uniqueString);
    }

    private void setRadioGroup() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id
                .radio_group_recipient);
        radioGroup.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_all:
                    setVisibility(View.GONE, mLayoutProfile, mLayoutGroup);
                    break;
                case R.id.rb_profile:
                    setVisibility(View.GONE, mLayoutGroup);
                    setVisibility(View.VISIBLE, mLayoutProfile);
                    break;
                case R.id.rb_group:
                    setVisibility(View.GONE, mLayoutProfile);
                    setVisibility(View.VISIBLE, mLayoutGroup);
                    break;
                default:
                    break;
            }
        });
    }

    private void setVisibility(int visibility, View... views) {
        for (View v : views) {
            v.setVisibility(visibility);
        }
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.edit_bulletin);
    }
}
