package ua.kpi.campus.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import ua.kpi.campus.R;
import ua.kpi.campus.jsonparsers.JSONGetPermissionsParser;
import ua.kpi.campus.jsonparsers.Permissions;

/**
 * Main activity for user, it appears after authorization
 *
 * @author Artur Dzidzoiev
 * @version 11/27/13
 */
public class MainInfoActivity extends Activity {
    private Permissions permissions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info);
        Intent intent = getIntent();
        String permissionsStr = intent.getStringExtra(LoginActivity.EXTRA_PERMISSIONS);
        permissions = JSONGetPermissionsParser.parse(permissionsStr);
        //
        showToastLong(permissionsStr);
    }

    private void showToastLong(String session_id) {
        Toast.makeText(getApplicationContext(), session_id, Toast.LENGTH_LONG).show();
    }

}