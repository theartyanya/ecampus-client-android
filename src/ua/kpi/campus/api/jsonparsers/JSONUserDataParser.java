package ua.kpi.campus.api.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;
import ua.kpi.campus.api.jsonparsers.user.*;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 09.12.13
 * Time: 0:25
 * To change this template use File | Settings | File Templates.
 */
public class JSONUserDataParser {
    private static final String STATUS_CODE_ATTRIBUTE_NAME = "StatusCode";
    private static final String TIMESTAMP_ATTRIBUTE_NAME = "TimeStamp";
    private static final String GUID_ATTRIBUTE_NAME = "Guid";
    private static final String PAGING_ATTRIBUTE_NAME = "Paging";

    public static JsonObject<UserData> parse(String jsonString) throws JSONException {
        JSONObject getPermissionsObj = new JSONObject(jsonString);
        String timeStampString = getPermissionsObj.getString(
                TIMESTAMP_ATTRIBUTE_NAME).replace('T', ' ');
        timeStampString = timeStampString.substring(0,
                timeStampString.length() - 6);
        JSONObject data = getPermissionsObj.getJSONObject("Data");
        ArrayList<Employee> dataArrayEmployees = JSONArrayParsers.parseEmployees(data);
        ArrayList<Personality> dataArrayPersonalities = JSONArrayParsers.parsePersonalities(data);
        ArrayList<SubsystemData> dataArrayProfiles =JSONArrayParsers.parseProfiles(data);
        UserData userData;

        if (dataArrayEmployees.size() == 0) {

            userData = new UserDataPersonalities(
                    data.getInt("UserAccountId"),
                    data.getString("Photo"),
                    data.getString("FullName"),
                    data.get("ScientificInterest"),
                    dataArrayPersonalities,
                    dataArrayProfiles);
            userData.setIsEmployee(false);
        } else {
            userData = new UserDataEmployee(
                    data.getInt("UserAccountId"),
                    data.getString("Photo"),
                    data.getString("FullName"),
                    data.get("ScientificInterest"),
                    dataArrayEmployees,
                    dataArrayProfiles);
            userData.setIsEmployee(true);
        }

        return new JsonObject<>(
                getPermissionsObj.getInt(STATUS_CODE_ATTRIBUTE_NAME),
                Timestamp.valueOf(timeStampString),
                getPermissionsObj.getString(GUID_ATTRIBUTE_NAME),
                getPermissionsObj.getString(PAGING_ATTRIBUTE_NAME), userData
        );
    }


}


