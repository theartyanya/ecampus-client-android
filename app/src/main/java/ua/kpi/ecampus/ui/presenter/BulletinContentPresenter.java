package ua.kpi.ecampus.ui.presenter;

import javax.inject.Inject;

/**
 * BulletinContentPresenter created to manage BulletinContentActivity.
 *
 * Created by Administrator on 04.02.2016.
 */
public class BulletinContentPresenter extends BasePresenter {

    private IView mView;

    @Inject
    public BulletinContentPresenter() {}

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
