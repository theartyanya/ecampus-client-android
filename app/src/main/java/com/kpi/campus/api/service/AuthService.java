package com.kpi.campus.api.service;

import com.kpi.campus.model.Airport;
import com.kpi.campus.model.LoginData;
import com.kpi.campus.model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 08.02.2016.
 */
public interface AuthService {

//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json"
//    })
    @POST("/oauth/token")
    Call<Token> auth(@Body LoginData loginData);

    @GET("/places/coords_to_places_ru.json")
    Call<List<Airport>> airports(@Query("coords") String gps);

    @GET("/test/5")
    Call<String> test();
}
