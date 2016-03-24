package com.kpi.campus.model.dao;

import com.kpi.campus.model.Bulletin;

import java.util.List;

/**
 * Created by Administrator on 21.03.2016.
 */
public class BulletinDao implements IDataAccessObject<Bulletin> {

    private List<Bulletin> mBulletins;

    @Override
    public List<Bulletin> getData() {
        return mBulletins;
    }

    @Override
    public void setData(List<Bulletin> data) {
        mBulletins = data;
    }

    @Override
    public Bulletin get(int number) {
        return mBulletins.get(number);
    }

    @Override
    public void update(Bulletin object) {

    }

    @Override
    public void delete(Bulletin object) {

    }
}
