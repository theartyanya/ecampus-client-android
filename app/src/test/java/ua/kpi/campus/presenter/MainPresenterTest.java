package ua.kpi.campus.presenter;

import android.content.Context;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import ua.kpi.campus.model.Subsystem;
import ua.kpi.campus.ui.Navigator;
import ua.kpi.campus.ui.Preference;
import ua.kpi.campus.ui.activity.MainActivity;
import ua.kpi.campus.ui.presenter.MainPresenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Admin on 17.05.2016.
 */
public class MainPresenterTest {

    private MainPresenter mPresenter;
    @Mock
    private MainPresenter.IView mView;
    @Mock
    private Navigator mNavigator;
    @Mock
    private Preference mPreference;
    @Mock
    private Context mContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new MainPresenter(mContext, mNavigator, mPreference);
        mView = mock(MainActivity.class);
        mPresenter.setView(mView);
    }

    @Test
    public void startActivityOfSubsystem() {
        mPresenter.startActivityBasedOn(0);
        verify(mNavigator).startBulletinBoardActivity();
    }

    @Test
    public void logout() {
        mPresenter.logout();
        verify(mPreference).saveLogoutInfo();
        verify(mNavigator).startLoginActivity();
    }


    @Test
    public void getSubsystems() {
        List<Subsystem> list = mPresenter.getData();
        Assert.assertEquals(6, list.size());
    }

    @Test
    public void userIsLogged() {
        mPresenter.checkUserIsLogged();
        verify(mPreference).getTokenExpirationDate();
        verify(mPreference).getIsLogged();
        verify(mNavigator).startLoginActivity();
    }
}
