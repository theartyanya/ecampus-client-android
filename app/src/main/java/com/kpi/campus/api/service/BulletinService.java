package com.kpi.campus.api.service;

import com.kpi.campus.model.pojo.Bulletin;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Contains list of APIs related to BulletinBoard.
 *
 * Created by Administrator on 21.03.2016.
 */
public interface BulletinService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/board/all")
    Observable<List<Bulletin>> getBulletins(
            @Header("Authorization") String authorization,
            @Query("limit") int limit,
            @Query("lastLoadedBulletinId") int lastId);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/board/moderator/all")
    Observable<List<Bulletin>> getModeratorBulletins(
            @Header("Authorization") String authorization,
            @Query("limit") int limit,
            @Query("lastLoadedBulletinId") int lastId);
}
