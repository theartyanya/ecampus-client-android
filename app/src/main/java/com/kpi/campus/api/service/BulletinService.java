package com.kpi.campus.api.service;

import com.kpi.campus.model.BulletinBoard;
import com.kpi.campus.model.Contributor;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by Administrator on 21.03.2016.
 */
public interface BulletinService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/account/info")
    Observable<List<BulletinBoard>> getBulletins(@Header("Authorization") String authorization);


    @GET("/repos/kpi-ua/ecampus-client-android/contributors")
Observable<List<Contributor>> getCont();

}
