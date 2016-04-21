package com.kpi.campus.ui.presenter;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.Item;
import com.kpi.campus.model.pojo.User;
import com.kpi.campus.rx.BulletinRxLoader;

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

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public void setView(IView view) {
        mView = view;
    }

    public void loadViewData() {
        List<Item> subdivisions = User.getInstance().subdivision;
        if (subdivisions != null && !subdivisions.isEmpty()) {
            Item mainSubdiv = subdivisions.get(0);
            mLoader.loadDescSubdivisions(mainSubdiv.getId().toString());
            mLoader.loadGroupsIn(mainSubdiv.getId().toString());
        }
        mLoader.loadProfiles();
    }

    public void loadGroupsInSubdiv(String subdivId) {
        mLoader.loadGroupsIn(subdivId);
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
        mView.setSubdivisionAdapter(list);
    }

    public void setProfiles(List<Item> list) {
        mView.setProfileAdapter(list);
    }

    public void setGroups(List<Item> list) {
        mView.setGroupAdapter(list);
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

        void setSubdivisionAdapter(List<Item> list);

        void setProfileAdapter(List<Item> list);

        void setGroupAdapter(List<Item> list);

        void updateBadgeCounter(int count);
    }
}
