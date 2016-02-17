package com.kpi.campus.api.service;

import com.kpi.campus.model.pojo.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Contains list of APIs related to authentication.
 *
 * Created by Administrator on 08.02.2016.
 */
public interface AuthService {

    @FormUrlEncoded
    @POST("/oauth/token")
    Call<Token> auth(@Field("username") String email, @Field("password") String password, @Field("grant_type") String type);
}
