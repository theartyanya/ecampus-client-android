package com.kpi.campus.api.service;

import com.kpi.campus.model.User;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Contains list of APIs related to User.
 *
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
