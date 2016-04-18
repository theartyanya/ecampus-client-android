package com.kpi.campus.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kpi.campus.Config;
import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.Recipient;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.Item;
import com.kpi.campus.model.pojo.User;
import com.kpi.campus.ui.adapter.BulletinsRecipientAdapter;
import com.kpi.campus.ui.adapter.ItemSpinnerAdapter;
import com.kpi.campus.ui.adapter.NothingSelectedAdapter;
import com.kpi.campus.ui.fragment.DatePickerFragment;
import com.kpi.campus.ui.presenter.NewBulletinPresenter;
import com.kpi.campus.util.DateUtil;
import com.kpi.campus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Activity for addition/edition of a Bulletin.
 */
public class NewBulletinActivity extends BaseActivity implements
        NewBulletinPresenter.IView {

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
    @Bind(R.id.recycler_view_buffer_recipients)
    RecyclerView mRecyclerView;
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
    NewBulletinPresenter mPresenter;

    private BulletinsRecipientAdapter mAdapter;
    private String mActivityTitle = "";
    private Bulletin mCurrentBulletin;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_markup);
        bindViews();
        mPresenter.setView(this);
        mActivityTitle = getIntent().getStringExtra(Config.KEY_TITLE);
        mCurrentBulletin = getIntent().getParcelableExtra(Config.KEY_BULLETIN);
        mPresenter.loadViewData();
        mPresenter.initializeViewComponent();
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
                ToastUtil.showShortMessage("Очищено", this);
                break;
            case R.id.action_done:
                //mPresenter.onStartRequest();
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
        setRecyclerView();
        setRadioGroup();
        setViewValues();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(NewBulletinActivity.this, R.style
                .AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.progress_sending));
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showResponse(int code, String msg) {
        switch (code) {
            case 200:
                ToastUtil.showShortMessage(getString(R.string
                        .bulletin_is_sent), this);
                break;
            case 400:
                ToastUtil.showShortMessage(getString(R.string.bad_recipient),
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
    public Bulletin composeBulletin() {
        String userId;
        userId = User.getInstance().id;
        List<Recipient> r = mAdapter.getData();
        Bulletin bulletin = new Bulletin(userId, mSubject.getText().toString
                (), mText.getText().toString(), DateUtil.getCurrentDate(),
                mStartDate.getText().toString(), mEndDate.getText().toString
                (), true, r);
        return bulletin;
    }

    @Override
    public void setSubdivisionAdapter(List<Item> list) {
        setSubdivisionSpinner(list);
    }

    @Override
    public void setProfileAdapter(List<Item> list) {
        setProfileSpinner(list);
    }

    @Override
    public void setGroupAdapter(List<Item> list) {
        setGroupSpinner(list);
    }

    private Recipient composeRecipient() {
        Recipient r = null;
        Item subdiv = (Item) mSpinnerSubdivision.getSelectedItem();
        String subdivId = Integer.toString(subdiv.getId());
        String subdivName = subdiv.getName();
        if (mRbAll.isChecked()) {
            r = new Recipient(subdivId, subdivName,
                    null, null, null, null);
        } else if (mRbProfile.isChecked()) {
            Item profile = (Item) mSpinnerProfile.getSelectedItem();
            if (profile == null) return null;
            r = new Recipient(subdivId, subdivName, Integer.toString(profile
                    .getId()), profile.getName(), null, null);
        } else if (mRbGroup.isChecked()) {
            Item group = (Item) mSpinnerGroup.getSelectedItem();
            if (group == null) return null;
            r = new Recipient(subdivId, subdivName, null, null, Integer
                    .toString(group.getId()), group.getName());
        }
        return r;
    }

    private void setViewValues() {
        User user = User.getInstance();
        TextView tv = (TextView) findViewById(R.id.text_view_author_name);
        tv.setText(user.name);

        if (isAddMode()) {
            setValuesForAddMode();
        } else {
            setValuesForEditMode();
        }
    }

    /**
     * mCurrentBulletin == null means that activity is opened on mode ADD
     * mCurrentBulletin != null means that this bulletin has to be edited
     *
     * @return true if activity is opened in Add mode,
     * false - if in Edit mode.
     */
    private boolean isAddMode() {
        return (mCurrentBulletin == null);
    }

    private void setValuesForAddMode() {
        TextView tv = (TextView) findViewById(R.id.text_view_actuality_value);
        tv.setText(R.string.yes);
        String currentDate = DateUtil.getCurrentDate();
        tv = (TextView) findViewById(R.id.text_view_creation_date_value);
        tv.setText(currentDate);
        tv = (TextView) findViewById(R.id
                .text_view_change_actuality_date_value);
        tv.setText(currentDate);
    }

    private void setValuesForEditMode() {
        mSubject.setText(mCurrentBulletin.getSubject());
        mText.setText(mCurrentBulletin.getText());
        mStartDate.setText(mCurrentBulletin.getDateStart());
        mEndDate.setText(mCurrentBulletin.getDateEnd());

        TextView tv = (TextView) findViewById(R.id.text_view_actuality_value);
        if (mCurrentBulletin.getActuality())
            tv.setText(R.string.yes);
        else
            tv.setText(R.string.no);
        tv = (TextView) findViewById(R.id.text_view_creation_date_value);
        tv.setText(mCurrentBulletin.getDateCreate());
        tv = (TextView) findViewById(R.id
                .text_view_change_actuality_date_value);
        tv.setText(mCurrentBulletin.getDateCreate());
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

    @OnClick(R.id.btn_add_recipient)
    public void onAddRecipient() {
        Recipient recipient = composeRecipient();
        if (recipient != null) {
            mAdapter.addItem(recipient);
        }
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

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(mActivityTitle);
    }

    private void setRecyclerView() {
        mAdapter = new BulletinsRecipientAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setProfileSpinner(List<Item> list) {
        ArrayAdapter<Item> adapter = new ItemSpinnerAdapter(this, R.layout
                .spinner_item, R.layout.spinner_dropdown_item, list);
        mSpinnerProfile.setAdapter(new NothingSelectedAdapter(
                adapter,
                R.layout.spinner_item_nothing_selected_profile,
                this));
    }

    private void setGroupSpinner(List<Item> list) {
        ArrayAdapter<Item> adapter = new ItemSpinnerAdapter(this, R.layout
                .spinner_item, R.layout.spinner_dropdown_item, list);
        mSpinnerGroup.setAdapter(new NothingSelectedAdapter(
                adapter,
                R.layout.spinner_item_nothing_selected_group,
                this));
    }

    private void setSubdivisionSpinner(List<Item> list) {
        ArrayAdapter<Item> adapter = new ItemSpinnerAdapter(this, R.layout
                .spinner_item, R.layout.spinner_dropdown_item, list);
        mSpinnerSubdivision.setAdapter(adapter);
        mSpinnerSubdivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                mPresenter.loadGroupsInSubdiv(item.getId().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setVisibility(int visibility, View... views) {
        for (View v : views) {
            v.setVisibility(visibility);
        }
    }

    private void clearValues() {
        String empty = "";
        mSubject.setText(empty);
        mText.setText(empty);
        mStartDate.setText(empty);
        mEndDate.setText(empty);
        mRbAll.setChecked(true);
        mAdapter.setItems(new ArrayList<>());
    }
}