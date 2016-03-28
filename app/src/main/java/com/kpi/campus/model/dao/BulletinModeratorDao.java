package com.kpi.campus.model.dao;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 28.03.2016.
 */
public class BulletinModeratorDao implements IDataAccessObject<Bulletin> {

    private List<Bulletin> mBulletins = new ArrayList<>();

    private List<Bulletin> mBulletinsByProfile = new ArrayList<>();
    private List<Bulletin> mBulletinsBySubdiv = new ArrayList<>();

    @Override
    public List<Bulletin> getData() {
        return mBulletins;
    }

    @Override
    public void setData(List<Bulletin> data) {
        mBulletins.addAll(data);

        List<String> userProfile = User.getInstance().position;
        String userSubdivision = User.getInstance().subdivision;
        if(userProfile != null && userSubdivision != null) {
            for (Bulletin bul : data) {
                if (userProfile.contains(bul.getProfile())) {
                    mBulletinsByProfile.add(bul);
                }
                if (userSubdivision.equalsIgnoreCase(bul.getSubdivision())) {
                    mBulletinsBySubdiv.add(bul);
                }
            }
        }
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

    public List<Bulletin> getFilteredByProfile() {
        return mBulletinsByProfile;
    }

    public List<Bulletin> getFilteredBySubdiv() {
        return mBulletinsBySubdiv;
    }
}
