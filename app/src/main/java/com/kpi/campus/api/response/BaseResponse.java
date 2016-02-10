package com.kpi.campus.api.response;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Represent a single data type for using it in loaders.
 * This type can save a query result. If we want to do something for a particular query, you need to be inherited from this class and override / add  methods.
 * Created by Administrator on 09.02.2016.
 */
public class BaseResponse {

    @Nullable
    private Object mAnswer;

    private RequestResult mRequestResult;

    public BaseResponse() {
        mRequestResult = RequestResult.ERROR;
    }

    @NonNull
    public RequestResult getRequestResult() {
        return mRequestResult;
    }

    public BaseResponse setRequestResult(RequestResult requestResult) {
        mRequestResult = requestResult;
        return this;
    }

    @Nullable
    public <T> T getTypedAnswer() {
        if (mAnswer == null) {
            return null;
        }
        //noinspection unchecked
        return (T) mAnswer;
    }

    public BaseResponse setAnswer(@Nullable Object answer) {
        mAnswer = answer;
        return this;
    }

    public void save(Context context) {
    }
}
