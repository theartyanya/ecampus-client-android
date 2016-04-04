package com.kpi.campus.util;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.kpi.campus.model.pojo.Bulletin;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 29.03.2016.
 */
public class BulletinPredicates {

    public static Predicate<Bulletin> isNotExpired(String date) {
        Date currentDate = DateUtil.convert(date);
        return p -> currentDate.after(DateUtil.convert(p.getDateStart())) &&
                currentDate.before(DateUtil.convert(p.getDateEnd()));
    }

    public static Predicate<Bulletin> isMatchesProfile(List<Integer> profiles) {
        return p -> profiles.contains(p.getProfileId());
    }

    public static Predicate<Bulletin> isMatchesSubdivision(List<Integer>
                                                                   subdiv) {
        return p -> subdiv.contains(p.getSubdivisionId());
    }

    public static Predicate<Bulletin> isDeleted() {
        return p -> p.getActuality() == false;
    }

    public static Predicate<Bulletin> isMatchesQuerySubject(String query) {
        return p -> p.getSubject().toLowerCase().contains(query);
    }

    public static List<Bulletin> filterBulletins(List<Bulletin> bulletins,
                                                 Predicate<Bulletin>
                                                         predicate) {
        return Stream.of(bulletins)
                .filter(predicate)
                .collect(Collectors.<Bulletin>toList());
    }


}
