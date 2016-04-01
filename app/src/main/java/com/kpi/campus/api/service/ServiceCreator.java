package com.kpi.campus.api.service;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This class creates a new REST client with a given API_Endpoint.
 * <p>
 * Created by Administrator on 08.02.2016.
 */
public class ServiceCreator {

    private static final int TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 120;
    private static final int CONNECT_TIMEOUT = 10;

    public static final String API_ENDPOINT =
            "http://api-campus-kpi-ua.azurewebsites.net";

    private static final OkHttpClient.Builder CLIENT = new OkHttpClient
            .Builder();

    static {
        CLIENT.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.readTimeout(TIMEOUT, TimeUnit.SECONDS);
    }

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    /**
     * Creates a retrofit service from an arbitrary class
     *
     * @param serviceClass Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(CLIENT.build()).build();
        return retrofit.create(serviceClass);
    }

}
