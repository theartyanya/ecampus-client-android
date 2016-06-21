package ua.kpi.ecampus.model.dao;

import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.util.DateUtil;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ua.kpi.ecampus.util.BulletinPredicates;

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
            List<Integer> ids = BulletinPredicates.getIdsCollection(userProfile);
            mByProfile.addAll(BulletinPredicates.filterBulletins(data,
                    BulletinPredicates.isMatchesProfile
                    (ids)));

            ids = BulletinPredicates.getIdsCollection(userSubdivision);
            mBySubdiv.addAll(BulletinPredicates.filterBulletins(data,
                    BulletinPredicates.isMatchesSubdivision
                    (ids)));
        }
        mNotExpired.addAll(BulletinPredicates.filterBulletins(data,
                BulletinPredicates.isNotExpired(DateUtil
                .getCurrentDate())));
        mDeleted.addAll(BulletinPredicates.filterBulletins(data, BulletinPredicates.isDeleted()));
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
