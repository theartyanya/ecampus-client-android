package ua.kpi.campus.ui.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import ua.kpi.campus.R;
import ua.kpi.campus.di.UIModule;
import ua.kpi.campus.model.Recipient;
import ua.kpi.campus.model.pojo.Bulletin;
import ua.kpi.campus.model.pojo.User;
import ua.kpi.campus.ui.presenter.SaveBulletinPresenter;
import ua.kpi.campus.util.DateUtil;
import ua.kpi.campus.util.ToastUtil;

/**
 * Activity for addition of a Bulletin.
 */
public class AddBulletinActivity extends SaveBulletinActivity implements
        SaveBulletinPresenter.IView {
    @Bind(R.id.btn_more_information)
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ua.kpi.campus.R.layout.activity_bulletin_markup);
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
        getMenuInflater().inflate(ua.kpi.campus.R.menu.menu_add_bulletin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case ua.kpi.campus.R.id.action_clear:
                clearValues();
                ToastUtil.showShortMessage(getString(ua.kpi.campus.R.string
                        .clear), this);
                break;
            case ua.kpi.campus.R.id.action_done:
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
            case 0:
                ToastUtil.showShortMessage(msg, this);
                break;
            case 200:
                ToastUtil.showShortMessage(getString(ua.kpi.campus.R.string
                        .bulletin_is_added), this);
                break;
            case 400:
                ToastUtil.showShortMessage(getString(ua.kpi.campus.R.string.bad_bulletin),
                        this);
                break;
            case 401:
                ToastUtil.showShortMessage(getString(ua.kpi.campus.R.string.unauthorized),
                        this);
                break;
            case 500:
                ToastUtil.showShortMessage(getString(ua.kpi.campus.R.string.server_error),
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

    @OnClick(R.id.btn_more_information)
    public void onMoreInformation(){
        hideShowMoreInformation((FrameLayout) findViewById(R.id.attribute_input_start_period));
        hideShowMoreInformation((FrameLayout) findViewById(R.id.attribute_input_end_period));
    }

    private void hideShowMoreInformation(FrameLayout frameLayout){
        if(frameLayout.getVisibility() != View.VISIBLE) {

            imageButton.setImageResource(R.drawable.more_button_up);
            frameLayout.setVisibility(View.VISIBLE);
        }
        else{

            imageButton.setImageResource(R.drawable.more_button);
            frameLayout.setVisibility(View.GONE);
        }
    }
    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(ua.kpi.campus.R.mipmap.ic_action_navigation_arrow_back);
        getSupportActionBar().setTitle(ua.kpi.campus.R.string.add_bulletin);
    }

    private void setInitialViewValues() {
        User user = User.getInstance();
        TextView tv = (TextView) findViewById(ua.kpi.campus.R.id.text_view_author_name);
        tv.setText(user.name);
        tv = (TextView) findViewById(ua.kpi.campus.R.id.text_view_actuality_value);
        tv.setText(ua.kpi.campus.R.string.yes);
        String currentDate = DateUtil.getCurrentDate(DateUtil.FORMAT);
        mCreateDate.setText(currentDate);
        tv = (TextView) findViewById(ua.kpi.campus.R.id
                .text_view_change_actuality_date_value);
        tv.setText(currentDate);
    }
}