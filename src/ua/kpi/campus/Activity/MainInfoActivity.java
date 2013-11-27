package ua.kpi.campus.Activity;

import android.app.Activity;
import android.os.Bundle;
import ua.kpi.campus.R;
import ua.kpi.campus.jsonparsers.GetPermissionsData;

/**
 * Main activity for user, it appears after authorization
 *
 * @author Artur Dzidzoiev
 * @version 11/27/13
 */
public class MainInfoActivity extends Activity {
    private GetPermissionsData permissionsData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info);


    }
}