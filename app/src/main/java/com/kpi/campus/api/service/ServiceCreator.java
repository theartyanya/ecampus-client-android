package com.kpi.campus.api.service;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class creates a new REST client with a given API_Endpoint.
 *
 * Created by Administrator on 08.02.2016.
 */
public class ServiceCreator {

    private static final int TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 120;
    private static final int CONNECT_TIMEOUT = 10;

    public static final String API_ENDPOINT = "http://api-campus-kpi-ua.azurewebsites.net";

    //public static final String API_ENDPOINT = "http://nano.aviasales.ru";


    private static final OkHttpClient.Builder CLIENT = new OkHttpClient.Builder();

    static {
        CLIENT.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.readTimeout(TIMEOUT, TimeUnit.SECONDS);
    }

//    private static final Gson GSON = new GsonBuilder()
//            .setExclusionStrategies(new ExclusionStrategy() {
//                @Override
//                public boolean shouldSkipField(FieldAttributes f) {
//                    return f.getDeclaringClass().equals(RealmObject.class);
//                }
//
//                @Override
//                public boolean shouldSkipClass(Class<?> clazz) {
//                    return false;
//                }
//            })
//            .create();

    @NonNull
    public static AuthService getAirportsService() {
        return getRetrofit().create(AuthService.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(CLIENT.build())
                .build();
    }

//    public static final String API_ENDPOINT = "http://api-campus-kpi-ua.azurewebsites.net";
//
//    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl(API_ENDPOINT)
//                    .addConverterFactory(GsonConverterFactory.create());
//
//    public static <S> S createService(Class<S> serviceClass) {
//        Retrofit retrofit = builder.client(httpClient.build()).build();
//        return retrofit.create(serviceClass);
//    }
}
