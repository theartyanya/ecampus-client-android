package com.kpi.campus.ui.presenter;

import com.kpi.campus.model.BulletinBoard;
import com.kpi.campus.model.dao.BulletinDao;
import com.kpi.campus.model.dao.IDataAccessObject;
import com.kpi.campus.rx.BaseRxLoader;
import com.kpi.campus.rx.BulletinRxLoader;
import com.kpi.campus.ui.Navigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * BbActualTabPresenter created to show BbActualTabFragment.
 *
 * Created by Admin on 02.02.2016.
 */
public class BbActualTabPresenter extends BasePresenter {

    private IView mView;
    private Navigator mNavigator;
    private IDataAccessObject<BulletinBoard> mDataAccess;

    @Inject
    public BbActualTabPresenter(Navigator navigator) {
        mNavigator = navigator;
        mDataAccess = new BulletinDao();
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
    }

    public List<BulletinBoard> getData() {
        List<BulletinBoard> data = mDataAccess.getData();
        if(data == null) {
            loadData();
        }
        return (data != null) ? data : new ArrayList<>();
        //data.add(new BulletinBoard("06.01.2016", "Увага модераторів", "Іваненко І.І."));
    }

    private void loadData() {
        BaseRxLoader loader = new BulletinRxLoader(mDataAccess);
        loader.apiCall();
    }

    public void onItemClick(int position) {
        mNavigator.startBulletinContentActivity(position);
    }

    public interface IView {

    }
}
