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
    private static final String PATTEN_INPUT_MESSAGE = "MM/dd/yyyy hh:mm:ss a";
    private static final String PATTEN_INPUT_BULLETIN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String PATTERN_SHORT_DATE = "HH:mm E', 'dd";
    private static final String PATTERN_SHORT = "HH:mm:ss";
    private static final String PATTERN_DATE_ONLY = "dd/MM/yyyy";

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

    public static long getUnixTimeMessage(String dateStr) {
        SimpleDateFormat inputDate = new SimpleDateFormat(PATTEN_INPUT_MESSAGE);
        Date date = new Date();
        try {
            date = inputDate.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getUnixTimeBulletinBoard(String dateStr) {
        SimpleDateFormat inputDate = new SimpleDateFormat(PATTEN_INPUT_BULLETIN);
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

    public static String getDateOnly(long unixtime) {
        SimpleDateFormat inputDate = new SimpleDateFormat(PATTERN_DATE_ONLY);
        Date date = new Date(unixtime);
        return inputDate.format(date);
    }

    public static String getShortDependsOnToday(long unixtime) {
        SimpleDateFormat longDate = new SimpleDateFormat(PATTERN_SHORT_DATE);
        SimpleDateFormat shortDate = new SimpleDateFormat(PATTERN_SHORT);
        Date newDate = new Date(unixtime);
        long today = getTodayDate();
        return unixtime < today ? longDate.format(newDate) : shortDate.format(newDate);
    }

    private static long getTodayDate() {
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        return today.getTime();
    }
}
