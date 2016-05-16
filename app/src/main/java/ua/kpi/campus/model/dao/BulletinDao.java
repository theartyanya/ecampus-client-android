package ua.kpi.campus.model.dao;

import ua.kpi.campus.model.pojo.Bulletin;
import ua.kpi.campus.model.pojo.Item;
import ua.kpi.campus.model.pojo.User;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ua.kpi.campus.util.BulletinPredicates;

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
            List<Integer> ids = BulletinPredicates.getIdsCollection(userProfile);
            mByProfile.addAll(BulletinPredicates.filterBulletins(data,
                    BulletinPredicates.isMatchesProfile
                    (ids)));
            ids = BulletinPredicates.getIdsCollection(userSubdivision);
            mBySubdivision.addAll(BulletinPredicates.filterBulletins(data,
                    BulletinPredicates.isMatchesSubdivision(ids)));
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
    public Collection<Bulletin> getFilteredByProfile() {

        return mByProfile;
    }

    /**
     * Get Bulletins filtered by user's subdivision.
     *
     * @return list of bulletins.
     */
    public Collection<Bulletin> getFilteredBySubdiv() {
        return mBySubdivision;
    }
}
