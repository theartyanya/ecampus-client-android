package com.kpi.campus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 08.02.2016.
 */
public class Token {

    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("token_type")
    private String mTokenType;
    @SerializedName("expires_in")
    private String mExpiresIn;

    public Token() {}

    public Token(String a, String type, String expires) {
        mAccessToken = a;
        mTokenType = type;
        mExpiresIn = expires;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setTokenType(String tokenType) {
        this.mTokenType = tokenType;
    }

    public String getExpiresIn() {
        return mExpiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.mExpiresIn = expiresIn;
    }
}
