package com.kpi.campus.ui.presenter;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.rx.BulletinRxLoader;

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
