package ua.kpi.campus.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Observable;

import ua.kpi.campus.api.response.BaseResponse;
import ua.kpi.campus.api.response.RequestResult;
import ua.kpi.campus.model.pojo.Token;
import ua.kpi.campus.rx.UserRxLoader;
import ua.kpi.campus.ui.Navigator;
import ua.kpi.campus.ui.Preference;
import ua.kpi.campus.ui.activity.LoginActivity;
import ua.kpi.campus.ui.presenter.LoginPresenter;

import static org.mockito.Mockito.doAnswer;
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
    @Mock
    private UserRxLoader mLoader;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new LoginPresenter(mNavigator, mPreference);
        mView = mock(LoginActivity.class);
        mPresenter.setView(mView);
        mPresenter.setLoader(mLoader);
    }

    @Test
    public void onStartLogin() {
        mPresenter.onStartLogin();
        verify(mView).showLoginProgressDialog();
        verify(mView).activateLoginButton(false);
    }

    @Test
    public void onFinishLogin() {
        mPresenter.onFinishLogin();
        verify(mView).dismissProgressDialog();
        verify(mView).activateLoginButton(true);
    }

    @Test
    public void setLoaderResult_Error() {
        BaseResponse response = getErrorResponse();
        mPresenter.setLoaderResult(response);
        verify(mView).onLoginFailed(response.getRequestResult());
    }

    @Test
    public void setLoaderResult_Ok() {
        BaseResponse response = getOkResponse();
        mPresenter.setLoaderResult(response);
        verify(mPreference).saveLoginInfo(response.getTypedAnswer());
        verify(mLoader).apiCall();
        verify(mNavigator).startMainActivity();
    }

    private BaseResponse getErrorResponse() {
        return new BaseResponse();
    }

    private BaseResponse getOkResponse() {
        BaseResponse response = new BaseResponse();
        response.setRequestResult(RequestResult.OK);
        response.setAnswer(new Token("valid_token"));
        return response;
    }
}
