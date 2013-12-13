package ua.kpi.campus.api.jsonparsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.kpi.campus.api.jsonparsers.user.Permissions;
import ua.kpi.campus.api.jsonparsers.user.SubsystemData;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Serhii Hokhalenko
 * @author Artur Dzidzoiev
 * @version 04 Dec 2013
 */
public class JSONGetPermissionsParser {
    private static final String STATUS_CODE_ATTRIBUTE_NAME = "StatusCode";
    private static final String TIMESTAMP_ATTRIBUTE_NAME = "TimeStamp";
    private static final String GUID_ATTRIBUTE_NAME = "Guid";
    private static final String PAGING_ATTRIBUTE_NAME = "Paging";

    public static Permissions parse(String jsonString) throws JSONException {
        JSONObject getPermissionsObj = new JSONObject(jsonString);
        String timeStampString = getPermissionsObj.getString(
                TIMESTAMP_ATTRIBUTE_NAME).replace('T', ' ');
        timeStampString = timeStampString.substring(0,
                timeStampString.length() - 6);
        ArrayList<SubsystemData> dataArray = parseChild(getPermissionsObj);

        return new Permissions(
                getPermissionsObj.getInt(STATUS_CODE_ATTRIBUTE_NAME),
                Timestamp.valueOf(timeStampString),
                getPermissionsObj.getString(GUID_ATTRIBUTE_NAME),
                getPermissionsObj.getString(PAGING_ATTRIBUTE_NAME),
                dataArray);
    }

    private static ArrayList<SubsystemData> parseChild(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Data");
        ArrayList<SubsystemData> dataArray = new ArrayList<SubsystemData>();

        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);
            dataArray.add(new SubsystemData(childJSONObject
                    .getString("SubsystemName"), childJSONObject
                    .getBoolean("IsCreate"), childJSONObject
                    .getBoolean("IsRead"), childJSONObject
                    .getBoolean("IsUpdate"), childJSONObject
                    .getBoolean("IsDelete")));
        }
        return dataArray;
    }
}
