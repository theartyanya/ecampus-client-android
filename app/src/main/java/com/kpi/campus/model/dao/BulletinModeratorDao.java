package com.kpi.campus.model.dao;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.Item;
import com.kpi.campus.model.pojo.User;
import com.kpi.campus.util.DateUtil;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.kpi.campus.util.BulletinPredicates.filterBulletins;
import static com.kpi.campus.util.BulletinPredicates.getIdsCollection;
import static com.kpi.campus.util.BulletinPredicates.isDeleted;
import static com.kpi.campus.util.BulletinPredicates.isMatchesProfile;
import static com.kpi.campus.util.BulletinPredicates.isMatchesSubdivision;
import static com.kpi.campus.util.BulletinPredicates.isNotExpired;

/**
 * Created by Administrator on 28.03.2016.
 */
public class BulletinModeratorDao implements IDataAccessObject<Bulletin> {

    /**
     * All unique bulletins where user is creator
     */
    private Set<Bulletin> mAll = new LinkedHashSet<>();
    /**
     * Bulletins that are not expired
     */
    private Set<Bulletin> mNotExpired = new LinkedHashSet<>();
    /**
     * Bulletins filtered by user profile
     */
    private Set<Bulletin> mByProfile = new LinkedHashSet<>();
    /**
     * Bulletins filtered by user subdivision
     */
    private Set<Bulletin> mBySubdiv = new LinkedHashSet<>();
    /**
     * Bulletins that are deleted
     */
    private Set<Bulletin> mDeleted = new LinkedHashSet<>();

    @Override
    public Collection<Bulletin> getData() {
        return mAll;
    }

    @Override
    public void setData(Collection<Bulletin> data) {
        if (data.isEmpty()) return;

        mAll.addAll(data);

        List<Item> userProfile = User.getInstance().position;
        List<Item> userSubdivision = User.getInstance().subdivision;

        if (userProfile != null && userSubdivision != null) {
            List<Integer> ids = getIdsCollection(userProfile);
            mByProfile.addAll(filterBulletins(mAll, isMatchesProfile
                    (ids)));

            ids = getIdsCollection(userSubdivision);
            mBySubdiv.addAll(filterBulletins(mAll, isMatchesSubdivision
                    (ids)));
        }
        mNotExpired.addAll(filterBulletins(mAll, isNotExpired(DateUtil
                .getCurrentDate())));
        mDeleted.addAll(filterBulletins(mAll, isDeleted()));
    }

    @Override
    public void update(Bulletin object) {
    }

    @Override
    public void delete(Bulletin object) {
    }

    public Collection<Bulletin> getFilteredByProfile() {
        return mByProfile;
    }

    public Collection<Bulletin> getFilteredBySubdiv() {
        return mBySubdiv;
    }

    public Collection<Bulletin> getNotExpired() {
        return mNotExpired;
    }

    public Collection<Bulletin> getDeleted() {
        return mDeleted;
    }
}
