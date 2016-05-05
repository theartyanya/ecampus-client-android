package com.kpi.campus.model.dao;

import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.Item;
import com.kpi.campus.model.pojo.User;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.kpi.campus.util.BulletinPredicates.filterBulletins;
import static com.kpi.campus.util.BulletinPredicates.getIdsCollection;
import static com.kpi.campus.util.BulletinPredicates.isMatchesProfile;
import static com.kpi.campus.util.BulletinPredicates.isMatchesSubdivision;

/**
 * Implementation of IDataAccessObject for the Bulletin data model.
 * <p>
 * Created by Administrator on 21.03.2016.
 */
public class BulletinDao implements IDataAccessObject<Bulletin> {

    /**
     * All unique bulletins available for current user
     */
    private Set<Bulletin> mAll = new LinkedHashSet<>();
    /**
     * Bulletins filtered by user profile
     */
    private Set<Bulletin> mByProfile = new LinkedHashSet<>();
    /**
     * Bulletins filtered by user subdivision
     */
    private Set<Bulletin> mBySubdivision = new LinkedHashSet<>();

    @Override
    public Set<Bulletin> getData() {
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
            mByProfile.addAll(filterBulletins(data, isMatchesProfile
                    (ids)));

            ids = getIdsCollection(userSubdivision);
            mBySubdivision.addAll(filterBulletins(data, isMatchesSubdivision
                    (ids)));
        }
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
    public Set<Bulletin> getFilteredByProfile() {
        return mByProfile;
    }

    /**
     * Get Bulletins filtered by user's subdivision.
     *
     * @return list of bulletins.
     */
    public Set<Bulletin> getFilteredBySubdiv() {
        return mBySubdivision;
    }
}
