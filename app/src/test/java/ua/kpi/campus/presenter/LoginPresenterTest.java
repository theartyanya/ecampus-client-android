package ua.kpi.campus.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.kpi.campus.ui.Navigator;
import ua.kpi.campus.ui.Preference;
import ua.kpi.campus.ui.activity.LoginActivity;
import ua.kpi.campus.ui.presenter.LoginPresenter;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Created by Admin on 17.05.2016.
 */
public class LoginPresenterTest {

    private LoginPresenter mPresenter;

    @Mock
    private LoginPresenter.IView mView;
    @Mock
    private Navigator mNavigator;
    @Mock
    private Preference mPreference;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new LoginPresenter(mNavigator, mPreference);
        mView = mock(LoginActivity.class);
        mPresenter.setView(mView);
    }

    @Test
    public void onStartLogin() {
        mPresenter.onStartLogin();
        verify(mView).showLoginProgressDialog();
        verify(mView).activateLoginButton(false);
    }
}
