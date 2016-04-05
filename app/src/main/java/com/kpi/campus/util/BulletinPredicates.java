package com.kpi.campus.util;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.Item;

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

    public static Predicate<Bulletin> isMatchesProfile(List<Integer>
                                                               profileIds) {
        return p -> Stream.of(profileIds).anyMatch(i -> i.equals(p
                .getProfileId()));
    }

    public static Predicate<Bulletin> isMatchesSubdivision(List<Integer>
                                                                   subdiv) {
        //return p -> subdiv.contains(p.getSubdivisionId());
        return p -> Stream.of(subdiv).anyMatch(i -> i.equals(p
                .getSubdivisionId()));
    }

    public static Predicate<Bulletin> isDeleted() {
        return p -> p.getActuality() == false;
    }

    public static Predicate<Bulletin> isMatchesQuerySubject(String query) {
        return p -> p.getSubject().toLowerCase().contains(query);
    }

    public static List<Integer> getIdsCollection(List<Item> items) {
        return Stream.of(items).map(Item::getId).collect
                (Collectors.<Integer>toList());
    }

    public static List<Bulletin> filterBulletins(List<Bulletin> bulletins,
                                                 Predicate<Bulletin>
                                                         predicate) {
        return Stream.of(bulletins)
                .filter(predicate)
                .collect(Collectors.<Bulletin>toList());
    }
}
