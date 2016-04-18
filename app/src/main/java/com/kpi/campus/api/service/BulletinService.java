package com.kpi.campus.api.service;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.Item;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Contains list of APIs related to BulletinBoard.
 * <p>
 * Created by Administrator on 21.03.2016.
 */
public interface BulletinService {

    @GET("/board/all")
    Observable<List<Bulletin>> getBulletins(
            @Header("Authorization") String authorization,
            @Query("limit") int limit,
            @Query("lastLoadedBulletinId") int lastId);

    @GET("/board/moderator/all")
    Observable<List<Bulletin>> getModeratorBulletins(
            @Header("Authorization") String authorization,
            @Query("limit") int limit,
            @Query("lastLoadedBulletinId") int lastId);

    @POST("/board/new")
    Observable<String> postNewBulletin(
            @Header("Authorization") String authorization,
            @Body Bulletin bulletin);

    @GET("/subdivision/desc")
    Observable<List<Item>> getDescendantSubdivisions(
            @Query("subdivisionId") String subdivisionId);

    @GET("/profile")
    Observable<List<Item>> getProfiles();

    @GET("/subdivision/groups")
    Observable<List<Item>> getGroupsIn(@Query("subdivisionId") String
                                               subdivisionId);
}
