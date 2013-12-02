package ua.kpi.campus.session;

import android.os.AsyncTask;
import ua.kpi.campus.CampusApi;
import ua.kpi.campus.jsonparsers.JSONAuthorizationParser;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/2/13
 */
public class Session {
    private Permissions permissions;
    private Authorization authorization;
    public final static String EXTRA_PERMISSIONS = "permissions";


    private void getPermissions(final String session_id) {
        new AsyncTask<String, Void, String>()
        {
            @Override public void onPostExecute(String result)
            {
//                String session_id = getSessionId(result);
//                getPermissions(session_id);
            }

            @Override
            protected String doInBackground(String... params) {
                return CampusApi.getPermission(session_id);
            }

        }.execute("");
    }

    private String getSessionId(String response) {
        Authorization authorization = JSONAuthorizationParser.parse(response);
        return authorization.getData();
    }

}
