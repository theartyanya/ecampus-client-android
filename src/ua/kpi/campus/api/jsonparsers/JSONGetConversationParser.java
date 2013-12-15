package ua.kpi.campus.api.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;
import ua.kpi.campus.api.jsonparsers.message.UserConversationData;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 15.12.13
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class JSONGetConversationParser {
    private static final String STATUS_CODE_ATTRIBUTE_NAME = "StatusCode";
    private static final String TIMESTAMP_ATTRIBUTE_NAME = "TimeStamp";
    private static final String GUID_ATTRIBUTE_NAME = "Guid";
    private static final String PAGING_ATTRIBUTE_NAME = "Paging";

    public static JsonObject parse(String jsonString) throws JSONException {
        JSONObject getPermissionsObj = new JSONObject(jsonString);
        String timeStampString = getPermissionsObj.getString(
                TIMESTAMP_ATTRIBUTE_NAME).replace('T', ' ');
        timeStampString = timeStampString.substring(0,
                timeStampString.length() - 6);
        JSONObject data = getPermissionsObj.getJSONObject("Data");

        ArrayList<UserConversationData> userData=JSONArrayParsers.parseUsers(data);



        return new JsonObject<ArrayList<UserConversationData>>(
                getPermissionsObj.getInt(STATUS_CODE_ATTRIBUTE_NAME),
                Timestamp.valueOf(timeStampString),
                getPermissionsObj.getString(GUID_ATTRIBUTE_NAME),
                getPermissionsObj.getString(PAGING_ATTRIBUTE_NAME), userData
        );
}
}
