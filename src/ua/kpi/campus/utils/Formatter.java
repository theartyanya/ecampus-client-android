package ua.kpi.campus.utils;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/26/13
 */
public class Formatter {
    public static String get(String original, final int maxLength) {
        if (original.length() > maxLength) {
            original = original.substring(0, maxLength);
            original = original + "...";
        }
        return original;
    }
}
