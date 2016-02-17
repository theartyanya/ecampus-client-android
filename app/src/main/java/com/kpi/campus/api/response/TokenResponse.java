package com.kpi.campus.api.response;

import android.content.Context;

import com.kpi.campus.database.table.TokenTable;
import com.kpi.campus.model.pojo.Token;

/**
 * Represent API response which is a token (security key).
 * This class is direct implementation of BaseResponse.
 * <p/>
 * Created by Administrator on 09.02.2016.
 */
public class TokenResponse extends BaseResponse {

    @Override
    public void save(Context context) {
        Token token = getTypedAnswer();
        if (token != null) {
            TokenTable.save(context, token);
        }
    }
}
