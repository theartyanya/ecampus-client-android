package com.kpi.campus.ui.presenter;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.Item;
import com.kpi.campus.model.pojo.User;
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
    private List<Item> mDescSubdivisions = new ArrayList<>();
    private List<Item> mProfiles = new ArrayList<>();

    @Inject
    public NewBulletinPresenter() {
        mLoader = new BulletinRxLoader(this);
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
        loadViewValues();
    }

    public void setView(IView view) {
        mView = view;
    }

    public void loadViewValues() {
        List<Item> subdivisions = User.getInstance().subdivision;
        if (subdivisions != null && !subdivisions.isEmpty()) {
            Item mainSubdiv = subdivisions.get(0);
            mLoader.loadDescSubdivisions(mainSubdiv.getId().toString());
        }
        mLoader.loadProfiles();
    }

    public void onStartRequest() {
        mView.showProgressDialog();

        addBulletin();
    }

    public void onFinishRequest(int responseCode, String responseMsg) {
        mView.dismissProgressDialog();
        mView.showResponse(responseCode, responseMsg);
    }

    public void setDescSubdivisions(List<Item> list) {
        mDescSubdivisions = list;
    }

    public void setProfiles(List<Item> list) {
        mProfiles = list;
    }

    public List<Item> getSubdivisionsList() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(10193, "ТК ФІОТ"));
        return items;
    }

    public List<Item> getProfileList() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(3, "Викладач-науковець"));
        items.add(new Item(5, "Студент навчальної групи"));
        items.add(new Item(6, "Староста навчальної групи"));
        items.add(new Item(7, "Профорг навчальної групи"));
        items.add(new Item(8, "Куратор навчальної групи"));
        return items;
    }

    public List<Item> getGroupList() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "IK-41"));
        items.add(new Item(2, "IK-42"));
        items.add(new Item(3, "IK-43"));
        return items;
    }

    private void addBulletin() {
        Bulletin newBulletin = mView.composeBulletin();
        mLoader.addBulletin(newBulletin);
    }

    public interface IView {
        void setViewComponent();

        void showProgressDialog();

        void dismissProgressDialog();

        void showResponse(int code, String msg);

        Bulletin composeBulletin();
    }
}
