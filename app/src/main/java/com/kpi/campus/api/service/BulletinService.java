package com.kpi.campus.api.service;

import com.kpi.campus.model.pojo.Bulletin;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 21.03.2016.
 */
public interface BulletinService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/board/all")
    Observable<List<Bulletin>> getBulletins(@Header("Authorization") String authorization, @Query("limit") int limit, @Query("lastLoadedBulletinId") int lastId);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/board/moderator/all")
    Observable<List<Bulletin>> getModeratorBulletins(@Header("Authorization") String authorization, @Query("limit") int limit, @Query("lastLoadedBulletinId") int lastId);
}
