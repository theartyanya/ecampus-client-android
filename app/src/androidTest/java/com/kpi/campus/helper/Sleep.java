package com.kpi.campus.helper;

/**
 * This class contains method to sleep the thread on specified time.
 * Created by Admin on 11.03.2016.
 */
public class Sleep {

    private static final int SLEEP_TIME = 5000;

    public static void sleepThread() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (Exception e) {
        }
    }
}
