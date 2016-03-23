package com.kpi.campus.model.dao;

import com.kpi.campus.model.BulletinBoard;

import java.util.List;

/**
 * Created by Administrator on 21.03.2016.
 */
public class BulletinDao implements IDataAccessObject<BulletinBoard> {

    private List<BulletinBoard> mBulletins;

    @Override
    public List<BulletinBoard> getData() {
        return mBulletins;
    }

    @Override
    public void setData(List<BulletinBoard> data) {
        mBulletins = data;
    }

    @Override
    public BulletinBoard get(int number) {
        return mBulletins.get(number);
    }

    @Override
    public void update(BulletinBoard object) {

    }

    @Override
    public void delete(BulletinBoard object) {

    }
}
