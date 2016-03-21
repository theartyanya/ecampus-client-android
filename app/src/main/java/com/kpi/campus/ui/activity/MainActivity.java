package com.kpi.campus.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kpi.campus.R;
import com.kpi.campus.di.UIModule;
import com.kpi.campus.model.Subsystem;
import com.kpi.campus.ui.adapter.GridSubsystemAdapter;
import com.kpi.campus.ui.presenter.MainPresenter;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Main activity for application.
 */
public class MainActivity extends BaseActivity implements MainPresenter.IView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.grid_view_subsystem)
    GridView mGridSubsystem;
    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    protected List<Object> getModules() {
        LinkedList<Object> modules = new LinkedList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                showLogoutDialog();
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.logout_confirmation);
        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.logout();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void setViewComponent() {
        setToolbar();
        setGridView();
    }

    private void setGridView() {
        List<Subsystem> data = mPresenter.getData();
        mGridSubsystem.setAdapter(new GridSubsystemAdapter(this, data));
        mGridSubsystem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mPresenter.startActivityBasedOn(position);
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_action_menu);
        getSupportActionBar().setTitle(R.string.activity_name_main);
    }
}
