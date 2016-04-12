package com.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kpi.campus.Config;
import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.User;
import com.kpi.campus.ui.adapter.BulletinsRecipientAdapter;
import com.kpi.campus.ui.adapter.SpinnerProfileAdapter;
import com.kpi.campus.ui.fragment.DatePickerFragment;
import com.kpi.campus.ui.presenter.NewBulletinPresenter;
import com.kpi.campus.util.DateUtil;
import com.kpi.campus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Activity for addition/edition of a Bulletin.
 */
public class NewBulletinActivity extends BaseActivity implements
        NewBulletinPresenter.IView {

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
//    @Bind(R.id.text_view_auto_recipient)
//    DelayAutoCompleteTextView mAutoCompleteRecipient;
//    @Bind(R.id.rb_all)
//    RadioButton mRbAll;
//    @Bind(R.id.rb_profile)
//    RadioButton mRbProfile;
    @Inject
    NewBulletinPresenter mPresenter;

    private BulletinsRecipientAdapter mAdapter;
    private String mActivityTitle = "";
    private Bulletin mCurrentBulletin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_markup);
        bindViews();
        mPresenter.setView(this);
        mActivityTitle = getIntent().getStringExtra(Config.KEY_TITLE);
        mCurrentBulletin = getIntent().getParcelableExtra(Config.KEY_BULLETIN);
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
                ToastUtil.showShortMessage("Очищено", this);
                break;
            case R.id.action_done:
                ToastUtil.showShortMessage("Збережено", this);
                finish();
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
        setProfileSpinner();
        setGroupSpinner();
        setSubdivisionSpinner();
//        setAutoCompleteRecipient();
//        setRadioGroup();
        setViewValues();
    }

    private void setViewValues() {
        User user = User.getInstance();
        TextView tv = (TextView) findViewById(R.id.text_view_author_name);
        tv.setText(user.name);
        // mCurrentBulletin == null means that activity is opened on mode ADD
        // mCurrentBulletin != null means that this bulletin has to be edited
        if (mCurrentBulletin == null) {
            setValuesForAddMode();
        } else {
            setValuesForEditMode();
        }
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
        EditText et = (EditText) findViewById(R.id.edit_text_bulletin_name);
        et.setText(mCurrentBulletin.getSubject());
        et = (EditText) findViewById(R.id.edit_text_bulletin_text);
        et.setText(mCurrentBulletin.getText());

        TextView tv = (TextView) findViewById(R.id.text_view_start_period);
        tv.setText(mCurrentBulletin.getDateStart());
        tv = (TextView) findViewById(R.id.text_view_end_period);
        tv.setText(mCurrentBulletin.getDateEnd());

        tv = (TextView) findViewById(R.id.text_view_actuality_value);
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

//    private void setRadioGroup() {
//        RadioGroup radioGroup = (RadioGroup) findViewById(R.id
//                .radio_group_recipient);
//        radioGroup.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
//            switch (checkedId) {
//                case R.id.rb_all:
//                    setVisibility(View.GONE, mSpinnerProfile);
//                    break;
//                case R.id.rb_profile:
//                    setVisibility(View.VISIBLE, mSpinnerProfile);
//                    break;
//                default:
//                    break;
//            }
//        });
//    }
//
//    @OnClick(R.id.button_add_recipient)
//    public void onAddItem() {
//        /// TODO: rewrite!
//
//        String recipient = "";
//        Editable autocompleteSubdivision = mAutoCompleteRecipient.getText();
//
//        if (mRbAll.isChecked()) {
//            if (autocompleteSubdivision != null && !autocompleteSubdivision
//                    .toString().isEmpty()) {
//                recipient = autocompleteSubdivision.toString();
//            }
//        } else if (mRbProfile.isChecked()) {
//            if (mSpinnerProfile.getSelectedItem() != null &&
//                    autocompleteSubdivision != null &&
//                    !autocompleteSubdivision.toString().isEmpty()) {
//                recipient = mSpinnerProfile.getSelectedItem().toString()
//                        .concat("-").concat(autocompleteSubdivision.toString());
//            }
//        }
//        mAdapter.addItem(recipient);
//    }

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setProfileSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.spinner_profile, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinnerProfile.setAdapter(new SpinnerProfileAdapter(
                adapter,
                R.layout.spinner_item_nothing_selected_profile,
                this));
    }

    private void setGroupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.spinner_group, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinnerGroup.setAdapter(new SpinnerProfileAdapter(
                adapter,
                R.layout.spinner_item_nothing_selected_group,
                this));
    }

    private void setSubdivisionSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.spinner_subdiv, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinnerSubdivision.setAdapter(adapter);
    }



//    private void setAutoCompleteRecipient() {
//        mAutoCompleteRecipient.setThreshold(1);
//        mAutoCompleteRecipient.setAdapter(new RecipientAutoCompleteAdapter
//                (getApplicationContext()));
//        mAutoCompleteRecipient.setLoadingIndicator((ProgressBar) findViewById
//                (R.id.progress_bar));
//        mAutoCompleteRecipient.setOnItemClickListener(new AdapterView
//                .OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view,
//                                    int position, long id) {
//                Recipient recipient = (Recipient) adapterView
//                        .getItemAtPosition(position);
//                mAutoCompleteRecipient.setText(recipient.getName());
//            }
//        });
//    }

    private void setVisibility(int visibility, View... views) {
        for (View v : views) {
            v.setVisibility(visibility);
        }
    }
}