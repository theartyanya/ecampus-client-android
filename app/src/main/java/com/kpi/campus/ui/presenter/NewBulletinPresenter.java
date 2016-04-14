package com.kpi.campus.ui.presenter;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.Item;
import com.kpi.campus.rx.BulletinRxLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * NewBulletinPresenter created to manage NewBulletinActivity.
 * <p>
 * Created by Admin on 12.02.2016.
 */
public class NewBulletinPresenter extends BasePresenter {

    private IView mView;
    private BulletinRxLoader mLoader;

    @Inject
    public NewBulletinPresenter() {
        mLoader = new BulletinRxLoader(this);
    }

    public void setView(IView view) {
        mView = view;
    }

    public void onStartRequest() {
        mView.showProgressDialog();

        addBulletin();
    }

    public void onFinishRequest(int responseCode, String responseMsg) {
        mView.dismissProgressDialog();
        mView.showResponse(responseCode, responseMsg);
    }

    public List<Item> getSubdivisionsList() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(10, "FIOT"));
        items.add(new Item(11, "TK FIOT"));
        items.add(new Item(12, "OT FIOT"));
        return items;
    }

    public List<Item> getProfileList() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "Teacher"));
        items.add(new Item(2, "Student"));
        items.add(new Item(3, "Leader"));
        return items;
    }

    public List<Item> getGroupList() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(101, "IK-41"));
        items.add(new Item(102, "IK-42"));
        items.add(new Item(103, "IK-43"));
        return items;
    }

    private void addBulletin() {
        Bulletin newBulletin = mView.composeBulletin();
        mLoader.addBulletin(newBulletin);
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public interface IView {
        void setViewComponent();

        void showProgressDialog();

        void dismissProgressDialog();

        void showResponse(int code, String msg);

        Bulletin composeBulletin();
    }
}
