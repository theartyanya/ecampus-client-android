package com.kpi.campus.api.service;

import com.kpi.campus.model.pojo.User;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Contains list of APIs related to User.
 * <p>
 * Created by Administrator on 17.03.2016.
 */
public interface UserService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/account/info")
    Observable<User> getUser(@Header("Authorization") String authorization);

}
