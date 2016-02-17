package com.kpi.campus.model.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * POJO class for convenient GSON serialization.
 * <p/>
 * Created by Administrator on 08.02.2016.
 */
public class Token {

    @SerializedName("access_token")
    private String mAccessToken;

    public Token() {
    }

    public Token(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }
}
