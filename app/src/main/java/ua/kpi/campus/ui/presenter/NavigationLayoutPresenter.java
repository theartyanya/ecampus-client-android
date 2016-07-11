package ua.kpi.campus.ui.presenter;

import android.content.Context;

import javax.inject.Inject;

import ua.kpi.campus.ui.Navigator;

/**
 * Created by Worck on 07.07.2016.
 */
public class NavigationLayoutPresenter extends BasePresenter {

    private IView mView;
    private Context mContext;
    private Navigator mNavigator;

    @Inject
    public NavigationLayoutPresenter(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
    }


    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }


    // open activity


    public interface IView {
        void setViewComponent();
    }
}
