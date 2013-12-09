package ua.kpi.campus.api.jsonparsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static User parse(String jsonString) throws JSONException {
        JSONObject getPermissionsObj = new JSONObject(jsonString);
        String timeStampString = getPermissionsObj.getString(
                TIMESTAMP_ATTRIBUTE_NAME).replace('T', ' ');
        timeStampString = timeStampString.substring(0,
                timeStampString.length() - 6);
        JSONObject data=getPermissionsObj.getJSONObject("Data");
        ArrayList<Employee> dataArrayEmployees = parseEmployees(data);
        ArrayList<Profile> dataArrayProfiles = parseProfiles(data);




        //TODO personalities
        return new User(
                getPermissionsObj.getInt(STATUS_CODE_ATTRIBUTE_NAME),
                Timestamp.valueOf(timeStampString),
                getPermissionsObj.getString(GUID_ATTRIBUTE_NAME),
                getPermissionsObj.getString(PAGING_ATTRIBUTE_NAME),
                new UserData(data.getInt("UserAccountId"),data.getString("Photo"),
                        data.getString("FullName"),data.get("ScientificInterest"),null,
                        dataArrayEmployees,dataArrayProfiles));
    }


    private static ArrayList<Employee> parseEmployees(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Employees");
        ArrayList<Employee> dataArray = new ArrayList<Employee>();

        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);
            dataArray.add(new Employee(childJSONObject
                    .getInt("SubdivisionId"), childJSONObject
                    .getString("SubdivisionName"), childJSONObject
                    .getString("Position"), childJSONObject
                    .getString("AcademicDegree"), childJSONObject
                    .getString("AcademicStatus")));
        }
        return dataArray;

    }
    private static ArrayList<Profile> parseProfiles(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Profiles");
        ArrayList<Profile> dataArray = new ArrayList<Profile>();

        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);
            dataArray.add(new Profile(childJSONObject
                    .getString("SubsystemName"), childJSONObject
                    .getBoolean("IsCreate"), childJSONObject
                    .getBoolean("IsRead"), childJSONObject
                    .getBoolean("IsUpdate"), childJSONObject
                    .getBoolean("IsDelete")));
        }
        return dataArray;
    }

}


