package com.kpi.campus.ui.presenter;

import javax.inject.Inject;

/**
 * Created by Admin on 12.02.2016.
 */
public class EditBulletinPresenter extends BasePresenter {

    private IView mView;

    @Inject
    public EditBulletinPresenter() {
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public interface IView {
        void setViewComponent();
    }
}
