package ua.kpi.ecampus.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import ua.kpi.ecampus.api.response.BaseResponse;
import ua.kpi.ecampus.api.response.RequestResult;

import java.io.IOException;

/**
 * Base loader manages request and response operations.
 * <p>
 * Created by Administrator on 09.02.2016.
 */
public abstract class BaseLoader extends AsyncTaskLoader<BaseResponse> {

    public BaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public BaseResponse loadInBackground() {
        try {
            BaseResponse response = apiCall();
            if (response.getRequestResult() == RequestResult.OK) {
                response.save(getContext());
                onSuccess();
            } else {
                onError();
            }
            return response;
        } catch (IOException e) {
            onError();
            return new BaseResponse();
        }
    }

    protected void onSuccess() {
    }

    protected void onError() {
    }

    protected abstract BaseResponse apiCall() throws IOException;
}
