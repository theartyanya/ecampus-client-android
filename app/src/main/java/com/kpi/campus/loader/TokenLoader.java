package com.kpi.campus.loader;

import android.content.Context;

import com.kpi.campus.api.response.BaseResponse;
import com.kpi.campus.api.response.RequestResult;
import com.kpi.campus.api.response.TokenResponse;
import com.kpi.campus.api.service.AuthService;
import com.kpi.campus.api.service.ServiceCreator;
import com.kpi.campus.model.pojo.Token;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;


/**
 * Loads Token data.
 *
 * Created by Administrator on 09.02.2016.
 */
public class TokenLoader extends BaseLoader {

    private final String mUsername;
    private final String mPassword;
    private final String mGrantType = "password";

    public TokenLoader(Context context, String username, String pass) {
        super(context);
        mUsername = username;
        mPassword = pass;
    }

    @Override
    protected BaseResponse apiCall() throws IOException {
        AuthService service = ServiceCreator.createService(AuthService.class);

        Call<Token> call = service.auth(mUsername, mPassword, mGrantType);
        Response<Token> resp = call.execute();
        Token token = resp.body();

        return new TokenResponse()
                .setRequestResult(RequestResult.OK)
                .setAnswer(token);
    }
}
