package ua.kpi.campus.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by doroshartyom on 17.01.2015.
 */
public class ConfigUtils {
    
    public static final String VERSION_NAME = "peka";
    
    public static String BUILD_TYPE = "nightly";
    
    public static final String LATEST_BUILD = "17.01.2015";
    
    public static String getVersionBuild(final Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;

    }
}
