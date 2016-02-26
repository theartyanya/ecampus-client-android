package com.kpi.campus.ui.activity;

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
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.Recipient;
import com.kpi.campus.ui.adapter.BulletinsRecipientAdapter;
import com.kpi.campus.ui.adapter.RecipientAutoCompleteAdapter;
import com.kpi.campus.ui.adapter.SpinnerProfileAdapter;
import com.kpi.campus.ui.fragment.DatePickerFragment;
import com.kpi.campus.ui.presenter.NewBulletinPresenter;
import com.kpi.campus.ui.view.DelayAutoCompleteTextView;
import com.kpi.campus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class NewBulletinActivity extends BaseActivity implements NewBulletinPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.edit_text_start_period)
    EditText mStartDate;
    @Bind(R.id.edit_text_end_period)
    EditText mEndDate;
    @Bind(R.id.recycler_view_buffer_recipients)
    RecyclerView mRecyclerView;
    @Bind(R.id.spinner_profile)
    Spinner mSpinnerProfile;
    @Bind(R.id.text_view_auto_recipient)
    DelayAutoCompleteTextView mAutoCompleteRecipient;

    @Inject
    NewBulletinPresenter mPresenter;

    private BulletinsRecipientAdapter mAdapter;
    private String mActivityTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_markup);
        bindViews();
        mPresenter.setView(this);
        mActivityTitle = getIntent().getStringExtra("KEY_TITLE");
        mPresenter.initializeViewComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        setAutoCompleteRecipient();
        setRadioGroup();
    }

    private void setRadioGroup() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_recipient);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_all:
                        setVisibility(View.GONE, mSpinnerProfile);
                        break;
                    case R.id.rb_profile:
                        setVisibility(View.VISIBLE, mSpinnerProfile);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @OnClick(R.id.button_add_recipient)
    public void onAddItem() {
        List<String> recipients = new ArrayList<>();
        recipients.add(mSpinnerProfile.getSelectedItem().toString());
        mAdapter.addItem(recipients);
    }

    private void setDateListener() {
        mStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    setDateTo(mStartDate, "2");
                }
            }
        });
        mEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    setDateTo(mEndDate, "1");
                }
            }
        });
    }

    private void setDateTo(EditText editText, String uniqueString) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setEditText(editText);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setProfileSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_profile, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerProfile.setAdapter(new SpinnerProfileAdapter(
                adapter,
                R.layout.spinner_item_nothing_selected,
                this));
    }

    private void setAutoCompleteRecipient() {
        mAutoCompleteRecipient.setThreshold(1);
        mAutoCompleteRecipient.setAdapter(new RecipientAutoCompleteAdapter(getApplicationContext()));
        mAutoCompleteRecipient.setLoadingIndicator((ProgressBar) findViewById(R.id.progress_bar));
        mAutoCompleteRecipient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Recipient recipient = (Recipient) adapterView.getItemAtPosition(position);
                mAutoCompleteRecipient.setText(recipient.getName());
            }
        });
    }

    private void setVisibility(int visibility, View... views) {
        for(View v : views) {
            v.setVisibility(visibility);
        }
    }


}