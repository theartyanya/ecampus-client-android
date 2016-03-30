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

    public static Predicate<Bulletin> isMatchesProfile(List<String> profiles) {
        return p -> profiles.contains(p.getProfile());
    }

    public static Predicate<Bulletin> isMatchesSubdivision(String subdiv) {
        return p -> p.getSubdivision().equalsIgnoreCase(subdiv);
    }

    public static Predicate<Bulletin> isDeleted() {
        return p -> p.getActuality() == false;
    }

    public static List<Bulletin> filterBulletins(List<Bulletin> bulletins, Predicate<Bulletin> predicate) {
        return Stream.of(bulletins)
                .filter(predicate)
                .collect(Collectors.<Bulletin>toList());
    }


}
