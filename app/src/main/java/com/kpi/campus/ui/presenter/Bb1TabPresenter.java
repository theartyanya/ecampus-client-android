package com.kpi.campus.ui.presenter;

import com.kpi.campus.model.Bulletin;
import com.kpi.campus.ui.Navigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Admin on 02.02.2016.
 */
public class Bb1TabPresenter extends BasePresenter {

    private IView mView;
    private Navigator mNavigator;

    @Inject
    public Bb1TabPresenter(Navigator navigator) {
        mNavigator = navigator;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public List<Bulletin> getData() {
        List<Bulletin> data = new ArrayList<>();
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        data.add(new Bulletin("06.01.2016", "Увага модераторів", "Іваненко І.І."));
        return data;
    }

    public void onItemClick(int position) {
        mNavigator.startBulletinContentActivity(position);
    }

    public void editBulletin() {
        mNavigator.startEditBulletinActivity();
    }

    public interface IView {
        void setViewComponent();
    }
}
