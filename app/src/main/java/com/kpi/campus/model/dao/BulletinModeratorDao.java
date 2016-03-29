package com.kpi.campus.model.dao;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.kpi.campus.util.BulletinPredicates.filterBulletins;
import static com.kpi.campus.util.BulletinPredicates.isDeleted;
import static com.kpi.campus.util.BulletinPredicates.isMatchesProfile;
import static com.kpi.campus.util.BulletinPredicates.isMatchesSubdivision;
import static com.kpi.campus.util.BulletinPredicates.isNotExpired;

/**
 * Created by Administrator on 28.03.2016.
 */
public class BulletinModeratorDao implements IDataAccessObject<Bulletin> {

    /**
     * All bulletins where user is creator
     */
    private List<Bulletin> mAll = new ArrayList<>();
    /**
     * Bulletins that are not expired
     */
    private List<Bulletin> mNotExpired = new ArrayList<>();
    /**
     * Bulletins filtered by user profile
     */
    private List<Bulletin> mByProfile = new ArrayList<>();
    /**
     * Bulletins filtered by user subdivision
     */
    private List<Bulletin> mBySubdiv = new ArrayList<>();
    /**
     * Bulletins that are deleted
     */
    private List<Bulletin> mDeleted = new ArrayList<>();

    @Override
    public List<Bulletin> getData() {
        return mAll;
    }

    @Override
    public void setData(List<Bulletin> data) {
        if (data.isEmpty()) return;

        mAll.addAll(data);

        List<String> userProfile = User.getInstance().position;
        String userSubdivision = User.getInstance().subdivision;

        if (userProfile != null && userSubdivision != null) {
            mByProfile.addAll(filterBulletins(mAll, isMatchesProfile(userProfile)));
            mBySubdiv.addAll(filterBulletins(mAll, isMatchesSubdivision(userSubdivision)));
        }
        mNotExpired.addAll(filterBulletins(mAll, isNotExpired(getCurrentDate())));
        mDeleted.addAll(filterBulletins(mAll, isDeleted()));
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public Bulletin get(int number) {
        return mAll.get(number);
    }

    @Override
    public void update(Bulletin object) {
    }

    @Override
    public void delete(Bulletin object) {
    }

    public List<Bulletin> getFilteredByProfile() {
        return mByProfile;
    }

    public List<Bulletin> getFilteredBySubdiv() {
        return mBySubdiv;
    }

    public List<Bulletin> getNotExpired() {
        return mNotExpired;
    }

    public List<Bulletin> getDeleted() {
        return mDeleted;
    }
}
