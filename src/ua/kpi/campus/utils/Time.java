package ua.kpi.campus.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/22/13
 */
public class Time {
    private static final String PATTEN_INPUT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String PATTERN_SHORT_DATE = "HH:mm E', 'dd";

    public static long getUnixTime(String dateStr) {
        SimpleDateFormat inputDate = new SimpleDateFormat(PATTEN_INPUT);
        Date date = new Date();
        try {
            date = inputDate.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getShortWithDate(long unixtime) {
        SimpleDateFormat inputDate = new SimpleDateFormat(PATTERN_SHORT_DATE);
        Date date = new Date(unixtime);
        return inputDate.format(date);
    }


}
