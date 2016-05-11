package com.kpi.campus.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.kpi.campus.ui.presenter.SaveBulletinPresenter;
import com.kpi.campus.util.DateUtil;
import com.kpi.campus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Activity for addition of a Bulletin.
 */
public class AddBulletinActivity extends BaseActivity implements
        SaveBulletinPresenter.IView {

    @Bind(R.id.edit_text_bulletin_theme)
    EditText mSubject;
    @Bind(R.id.edit_text_bulletin_text)
    EditText mText;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.text_view_start_period)
    EditText mStartDate;
    @Bind(R.id.text_view_end_period)
    EditText mEndDate;
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
    SaveBulletinPresenter mPresenter;

    private BulletinsRecipientAdapter mAdapter;
    private ProgressDialog mProgressDialog;

    private final String START_DATE = "start_date";
    private final String END_DATE = "end_date";

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
                if (validateInput())
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
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(AddBulletinActivity.this, R.style
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

    @Override
    public void setRecipientsList(List<Recipient> list) {
        // N / A
    }

    @Override
    public void updateBadgeCounter(int count) {
        TextView tvCounter = (TextView) findViewById(R.id.tv_badge_counter);
        tvCounter.setText(Integer.toString(count));
    }

    @OnClick(R.id.btn_add_recipient)
    public void onAddRecipient() {
        Recipient recipient = createRecipient();
        if (recipient != null) {
            if (!mAdapter.contains(recipient)) {
                mAdapter.addItem(recipient);
                updateBadgeCounter(mAdapter.getItemCount());
            } else {
                ToastUtil.showShortMessage(getString(R.string
                        .recipient_already_exists), this);
            }
        } else {
            ToastUtil.showError(getString(R.string.wrong_recipient), this);
        }
    }

    @OnClick(R.id.btn_show_recipients)
    public void onShowRecipients() {
        View inflatedView = inflateView(R.layout.recipient_popup_layout);
        setRecyclerView(inflatedView);

        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        PopupWindow popWindow = new PopupWindow(inflatedView, size.x - 50,
                size.y - 900, true);
        popWindow.setBackgroundDrawable(ContextCompat.getDrawable
                (getApplicationContext(), R.drawable.popup_bg));
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);
        popWindow.setAnimationStyle(R.style.PopupAnimation);
        popWindow.showAtLocation(new LinearLayout(this), Gravity.BOTTOM, 0,
                100);
    }

    private void setDateListener() {
        mStartDate.setOnClickListener(v -> setDateTo(mStartDate, "2"));
        mEndDate.setOnClickListener(v -> setDateTo(mEndDate, "1"));
    }

    private void setDateTo(EditText view, String uniqueString) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setView(view);
        newFragment.show(getFragmentManager(), uniqueString);
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(R.string.add_bulletin);
    }

    private void setAdapter() {
        mAdapter = new BulletinsRecipientAdapter(this);
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
        mSpinnerSubdivision.setOnItemSelectedListener(new AdapterView
                .OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                mPresenter.loadGroupsOfSubdivision(item.getId().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                    setVisibility(View.GONE, findViewById(R.id
                            .image_view_subdiv));
                    setVisibility(View.GONE, findViewById(R.id
                            .spinner_subdivision));
                    break;
                default:
                    break;
            }
        });
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

    private void setVisibility(int visibility, View... views) {
        for (View v : views) {
            v.setVisibility(visibility);
        }
    }

    private Recipient createRecipient() {
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
            r = new Recipient(null, null, null, null, Integer
                    .toString(group.getId()), group.getName());
        }
        return r;
    }

    private void clearValues() {
        String empty = "";
        mSubject.setText(empty);
        mText.setText(empty);
        mStartDate.setText(empty);
        mEndDate.setText(empty);
        mRbAll.setChecked(true);
        mAdapter.clear();
    }

    private View inflateView(int resource) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(resource, null, false);
    }

    private void setRecyclerView(View parentView) {
        RecyclerView recView = (RecyclerView) parentView.findViewById(R.id
                .recycler_view_buffer_recipients);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(layoutManager);
        recView.setAdapter(mAdapter);
    }

    /**
     * Validate user input
     *
     * @return
     */
    private boolean validateInput() {
        boolean isValid = true;
        if (mAdapter.getItemCount() <= 0) {
            ToastUtil.showShortMessage(getString(R.string
                    .recipient_must_be_added), this);
            isValid = false;
        }
        return (isValid &
                validateField((TextInputLayout) findViewById(R.id
                        .input_theme), mSubject.getText().toString()) &
                validateField((TextInputLayout) findViewById(R.id
                        .input_text), mText.getText().toString()) &
                validateField((TextInputLayout) findViewById(R.id
                        .input_start_period), mStartDate.getText().toString())
                & validateField((TextInputLayout) findViewById(R.id
                .input_end_period), mEndDate.getText().toString())
        );
    }

    private boolean validateField(TextInputLayout inputLayout, String
            inputField) {
        if (inputField.isEmpty()) {
            inputLayout.setError(getString(R.string.required_field));
            return false;
        } else {
            inputLayout.setError(null);
        }
        return true;
    }
}