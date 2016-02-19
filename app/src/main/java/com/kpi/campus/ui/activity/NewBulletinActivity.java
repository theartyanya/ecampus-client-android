package com.kpi.campus.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.ui.adapter.BulletinsRecipientAdapter;
import com.kpi.campus.ui.adapter.HintSpinnerAdapter;
import com.kpi.campus.ui.fragment.DatePickerFragment;
import com.kpi.campus.ui.presenter.NewBulletinPresenter;
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
    @Bind(R.id.spinner1)
    Spinner spinner1;
    @Bind(R.id.spinner2)
    Spinner spinner2;
    @Bind(R.id.spinner3)
    Spinner spinner3;

    @Inject
    NewBulletinPresenter mPresenter;

    private BulletinsRecipientAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_markup);
        bindViews();
        mPresenter.setView(this);
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
        setSpinner();
    }

    @OnClick(R.id.button_add_recipient)
    public void onAddItem() {
        List<String> recipients = new ArrayList<>();

        if (!isTitleSelected(spinner1)) {
            recipients.add(spinner1.getSelectedItem().toString());
        }
        if (!isTitleSelected(spinner2)) {
            recipients.add(spinner2.getSelectedItem().toString());
        }
        if (!isTitleSelected(spinner3)) {
            recipients.add(spinner3.getSelectedItem().toString());
        }
        mAdapter.addItem(recipients);
    }

    private boolean isTitleSelected(Spinner spinner) {
        if (spinner.getSelectedItemPosition() == HintSpinnerAdapter.HINT_ITEM_POSITION) {
            return true;
        }
        return false;
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
        getSupportActionBar().setTitle(R.string.add_new_bulletin);
    }

    private void setRecyclerView() {
        mAdapter = new BulletinsRecipientAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setSpinner() {
        Resources resources = getResources();
        setSpinnerValue(spinner1, resources.getStringArray(R.array.spinner_profile));
        setSpinnerValue(spinner2, resources.getStringArray(R.array.spinner_subsystem));
        setSpinnerValue(spinner3, resources.getStringArray(R.array.spinner_group));
    }

    private void setSpinnerValue(Spinner spinner, String[] objects) {
        HintSpinnerAdapter adapter = new HintSpinnerAdapter(getApplicationContext(), R.layout.spinner_item, objects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}