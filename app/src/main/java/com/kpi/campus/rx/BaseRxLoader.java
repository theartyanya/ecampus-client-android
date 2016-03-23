package com.kpi.campus.rx;

import com.kpi.campus.model.dao.IDataAccessObject;

/**
 * Created by Administrator on 21.03.2016.
 */
public abstract class BaseRxLoader {

    protected IDataAccessObject dataAccessObject;

    public abstract void apiCall();
}
