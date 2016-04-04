package com.kpi.campus.model.dao;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.Item;
import com.kpi.campus.model.pojo.User;

import java.util.ArrayList;
import java.util.List;

import static com.kpi.campus.util.BulletinPredicates.filterBulletins;
import static com.kpi.campus.util.BulletinPredicates.isMatchesProfile;
import static com.kpi.campus.util.BulletinPredicates.isMatchesSubdivision;

/**
 * Implementation of IDataAccessObject for the Bulletin data model.
 * <p>
 * Created by Administrator on 21.03.2016.
 */
public class BulletinDao implements IDataAccessObject<Bulletin> {

    /**
     * All bulletins available for current user
     */
    private List<Bulletin> mAll = new ArrayList<>();
    /**
     * Bulletins filtered by user profile
     */
    private List<Bulletin> mByProfile = new ArrayList<>();
    /**
     * Bulletins filtered by user subdivision
     */
    private List<Bulletin> mBySubdivision = new ArrayList<>();

    @Override
    public List<Bulletin> getData() {
        return mAll;
    }

    @Override
    public void setData(List<Bulletin> data) {
        if (data.isEmpty()) return;

        mAll.addAll(data);

        List<Item> userProfile = User.getInstance().position;
        List<Item> userSubdivision = User.getInstance().subdivision;

        if (userProfile != null && userSubdivision != null) {
            List<Integer> profileIds = new ArrayList<>(userProfile.size());
            for (Item item : userProfile) {
                profileIds.add(item.getId());
            }
            List<Integer> subdivIds = new ArrayList<>(userSubdivision.size());
            for (Item item : userSubdivision) {
                subdivIds.add(item.getId());
            }

            mByProfile.addAll(filterBulletins(mAll, isMatchesProfile
                    (profileIds)));
            mBySubdivision.addAll(filterBulletins(mAll, isMatchesSubdivision
                    (subdivIds)));
        }
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

    /**
     * Get Bulletins filtered by user's profile.
     *
     * @return list of bulletins.
     */
    public List<Bulletin> getFilteredByProfile() {
        return mByProfile;
    }

    /**
     * Get Bulletins filtered by user's subdivision.
     *
     * @return list of bulletins.
     */
    public List<Bulletin> getFilteredBySubdiv() {
        return mBySubdivision;
    }
}
