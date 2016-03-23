package com.kpi.campus.ui.presenter;

import com.kpi.campus.model.BulletinBoard;
import com.kpi.campus.ui.Navigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Bb3TabPresenter created to show Bb3TabFragment.
 * Created by Administrator on 19.02.2016.
 */
public class Bb3TabPresenter extends BasePresenter {

    private IView mView;
    private Navigator mNavigator;

    @Inject
    public Bb3TabPresenter(Navigator navigator) {
        mNavigator = navigator;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public List<BulletinBoard> getData() {
        List<BulletinBoard> data = new ArrayList<>();
//        data.add(new BulletinBoard("06.01.2016", "Увага модераторів", "Андрієнко А.А."));
//        data.add(new BulletinBoard("06.01.2016", "Увага модераторів", "Андрієнко А.А."));
//        data.add(new BulletinBoard("06.01.2016", "Увага модераторів", "Андрієнко А.А."));
//        data.add(new BulletinBoard("06.01.2016", "Увага модераторів", "Андрієнко А.А."));
//        data.add(new BulletinBoard("06.01.2016", "Увага модераторів", "Андрієнко А.А."));
//        data.add(new BulletinBoard("06.01.2016", "Увага модераторів", "Андрієнко А.А."));
//        data.add(new BulletinBoard("06.01.2016", "Увага модераторів", "Андрієнко А.А."));
        return data;
    }

    public void onItemClick(int position) {
        mNavigator.startBulletinContentActivity(position);
    }

    public void editBulletin(String title) {
        mNavigator.startNewBulletinActivity(title);
    }

    public interface IView {
        void setViewComponent();
    }
}