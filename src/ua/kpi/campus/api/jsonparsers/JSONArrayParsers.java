package ua.kpi.campus.api.jsonparsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.kpi.campus.api.jsonparsers.message.User;
import ua.kpi.campus.api.jsonparsers.message.UserConversationData;
import ua.kpi.campus.api.jsonparsers.timetable.Parameter;
import ua.kpi.campus.api.jsonparsers.timetable.TimeTableData;
import ua.kpi.campus.api.jsonparsers.user.*;

import java.util.ArrayList;
/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 15.12.13
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
class JSONArrayParsers {
    protected static ArrayList<Employee> parseEmployees(JSONObject getPermissionsObj) throws JSONException {
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

    protected static ArrayList<Personality> parsePersonalities(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Personalities");
        ArrayList<Personality> dataArray = new ArrayList<Personality>();

        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);
            dataArray.add(new Personality(childJSONObject
                    .getInt("SubdivisionId"), childJSONObject
                    .getString("SubdivisionName"), childJSONObject
                    .getString("StudyGroupName"), childJSONObject
                    .getBoolean("IsContract"), childJSONObject
                    .getString("Specialty")));
        }
        return dataArray;

    }

    protected static ArrayList<SubsystemData> parseProfiles(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Profiles");
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
    //TODO    LastMessageDate as timestamp

    protected static ArrayList<UserConversationData> parseUsers(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Data");
        ArrayList<UserConversationData> dataArray = new ArrayList<UserConversationData>();
        ArrayList<User> users=new ArrayList<User>();

        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);

            JSONArray  dataJSONArray1  =childJSONObject.getJSONArray("Users");

            for (int j = 0; j < dataJSONArray1.length(); j++) {
                JSONObject childJSONObject1 = dataJSONArray1.getJSONObject(j);



                users.add(new User(childJSONObject1.getInt("UserAccountId"),childJSONObject1.getString("Photo"),
                        childJSONObject1.getString("FullName"),childJSONObject1.get("ScientificInterest"), childJSONObject1.get("Profiles")
                        ,childJSONObject1.get("Employees"),childJSONObject1.get("Personalities")));
            }
            dataArray.add(new UserConversationData(
                    childJSONObject.getString("Subject"),users,childJSONObject.getString("LastMessageText"),
                    childJSONObject.getString("LastMessageDate"),childJSONObject.getInt("GroupId")));

        }
        return dataArray;
    }
    protected static ArrayList<TimeTableData> parseTimeTable(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Data");
        ArrayList<TimeTableData> dataArray = new ArrayList<TimeTableData>();

        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);

            JSONArray  dataJSONArray1  =childJSONObject.getJSONArray("Parameters");
                     ArrayList<Parameter> parameters=new ArrayList<Parameter>();
            for (int j = 0; j < dataJSONArray1.length(); j++) {
                JSONObject childJSONObject1 = dataJSONArray1.getJSONObject(j);

                parameters.add(new Parameter(childJSONObject1.getString("Name"),childJSONObject1.getString("Type")));

            }
            dataArray.add(new TimeTableData(childJSONObject.getString("Name"),parameters));

        }
        return dataArray;
    }
}
