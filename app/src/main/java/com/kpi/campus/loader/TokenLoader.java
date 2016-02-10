package com.kpi.campus.loader;

import android.content.Context;

import com.kpi.campus.api.response.BaseResponse;
import com.kpi.campus.api.response.RequestResult;
import com.kpi.campus.api.response.TokenResponse;
import com.kpi.campus.api.service.AuthService;
import com.kpi.campus.api.service.ServiceCreator;
import com.kpi.campus.model.LoginData;
import com.kpi.campus.model.Token;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 09.02.2016.
 */
public class TokenLoader extends BaseLoader {

    private final String mUsername;
    private final String mPassword;
    private AuthService mAuthService;

    public TokenLoader(Context context, String username, String pass) {
        super(context);
        mUsername = username;
        mPassword = pass;
    }

    @Override
    protected BaseResponse apiCall() throws IOException {
        //AuthService service = ServiceCreator.createService(AuthService.class);
        AuthService service = ServiceCreator.getAirportsService();

        Call<Token> call = service.auth(getLoginData());

        Response<Token> c = call.execute();
        Token airports = c.body();

        return new TokenResponse()
                .setRequestResult(RequestResult.OK)
                .setAnswer(airports);
    }

//    @Override
//    protected BaseResponse apiCall() throws IOException {
//        AuthService service = ServiceCreator.getAirportsService();
//
//        Call<Token> call = service.auth(getLoginData());
//        call.enqueue(new Callback<Token>() {
//            @Override
//            public void onResponse(Call<Token> call, Response<Token> response) {
//                if (!response.isSuccess()) {
//                    return;
//                }
//
//                Token decodedResponse = response.body();
//
//                if (decodedResponse == null) return;
//            }
//
//            @Override
//            public void onFailure(Call<Token> call, Throwable t) {
//
//            }
//        });
//        return null;
//    }

//    @Override
//    protected BaseResponse apiCall() throws IOException {
//        AuthService service = ServiceCreator.getAirportsService();
//        Call<List<Airport>> call = service.airports("55.749792,37.6324949");
//        List<Airport> airports = call.execute().body();
//        return new TokenResponse()
//                .setRequestResult(RequestResult.OK)
//                .setAnswer(airports);
//    }

    private LoginData getLoginData() {
        return new LoginData(mUsername, mPassword);
    }
}
