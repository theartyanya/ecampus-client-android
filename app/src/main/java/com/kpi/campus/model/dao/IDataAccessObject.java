package com.kpi.campus.model.dao;

import java.util.List;

/**
 * Created by Administrator on 21.03.2016.
 */
public interface IDataAccessObject<T> {

    List<T> getData();

    void setData(List<T> data);

    T get(int number);

    void update(T object);

    void delete(T object);
}
