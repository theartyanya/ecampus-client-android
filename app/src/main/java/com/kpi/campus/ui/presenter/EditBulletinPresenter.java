package com.kpi.campus.ui.presenter;

import com.kpi.campus.rx.BulletinRxLoader;

import javax.inject.Inject;

/**
 * EditBulletinPresenter created to manage EditBulletinActivity.
 * <p>
 * Created by Administrator on 22.04.2016.
 */
public class EditBulletinPresenter extends BasePresenter {

    private IView mView;
    private BulletinRxLoader mLoader;

    @Inject
    public EditBulletinPresenter() {
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public void setView(IView view) {
        mView = view;
    }

    public interface IView {
        void setViewComponent();
    }
}
