package ua.kpi.campus.api.jsonparsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.kpi.campus.api.jsonparsers.message.MessageDetail;
import ua.kpi.campus.api.jsonparsers.message.MessageItem;
import ua.kpi.campus.api.jsonparsers.message.UserConversationData;
import ua.kpi.campus.api.jsonparsers.message.UserMessage;
import ua.kpi.campus.api.jsonparsers.user.Employee;
import ua.kpi.campus.api.jsonparsers.user.Personality;
import ua.kpi.campus.api.jsonparsers.user.SubsystemData;
import ua.kpi.campus.model.BulletinBoardSubject;
import ua.kpi.campus.utils.Time;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 15.12.13
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
class JSONArrayParsers {
    static ArrayList<Employee> parseEmployees(JSONObject getPermissionsObj) throws JSONException {
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

    static ArrayList<Personality> parsePersonalities(JSONObject getPermissionsObj) throws JSONException {
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

    static ArrayList<SubsystemData> parseProfiles(JSONObject getPermissionsObj) throws JSONException {
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

    static ArrayList<UserConversationData> parseUsers(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Data");
        ArrayList<UserConversationData> dataArray = new ArrayList<UserConversationData>();
        ArrayList<UserMessage> users = new ArrayList<UserMessage>();

        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);

            JSONArray dataJSONArray1 = childJSONObject.getJSONArray("Users");

            for (int j = 0; j < dataJSONArray1.length(); j++) {
                JSONObject childJSONObject1 = dataJSONArray1.getJSONObject(j);


                users.add(new UserMessage(childJSONObject1.getInt("UserAccountId"), childJSONObject1.getString("Photo"),
                        childJSONObject1.getString("FullName"), childJSONObject1.get("ScientificInterest"), childJSONObject1.get("Profiles")
                        , childJSONObject1.get("Employees"), childJSONObject1.get("Personalities")));
            }
            dataArray.add(new UserConversationData(
                    childJSONObject.getString("Subject"), users, childJSONObject.getString("LastMessageText"),
                    childJSONObject.getString("LastMessageDate"), childJSONObject.getInt("GroupId")));

        }
        return dataArray;
    }

    protected static ArrayList<MessageItem> parseMessageGetItem(JSONObject JsonObj) throws JSONException {
        JSONArray dataJSONArray = JsonObj.getJSONArray("Data");
        ArrayList<MessageItem> dataArray = new ArrayList<MessageItem>();


        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);
            MessageDetail messageDetail = new MessageDetail(0, "");
            dataArray.add(new MessageItem(childJSONObject.getInt("SenderUserAccountId"), childJSONObject.getInt("MessageId"),
                    childJSONObject.getInt("MassageGroupId"), messageDetail, childJSONObject.getString("DateSent"), childJSONObject.getString("Subject"), childJSONObject.getString("Text")));


        }
        return dataArray;
    }

    static ArrayList<BulletinBoardSubject> parseBulletinBoard(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Data");
        ArrayList<BulletinBoardSubject> dataArray = new ArrayList<BulletinBoardSubject>();

        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);
            long dateCreate = Time.getUnixTimeBulletinBoard(childJSONObject.getString("DateCreate"));
            dataArray.add(new BulletinBoardSubject(childJSONObject.getString("Text"), dateCreate
                    , childJSONObject.getInt("CreatorUserAccountId"),
                    childJSONObject.getString("CreatorUserFullName"), childJSONObject
                    .getInt("BulletinBoardSubjectId"), childJSONObject.getInt("BulletinBoardId"),
                    childJSONObject.getString("BulletinBoardLinkRecipients"),
                    childJSONObject.getString("Subject")));
        }
        return dataArray;

    }

    static ArrayList<TimeTableData> parseTimeTableData(JSONObject getPermissionsObj) throws JSONException {
        JSONArray dataJSONArray = getPermissionsObj.getJSONArray("Data");
        ArrayList<TimeTableData> dataArray = new ArrayList<TimeTableData>();

        for (int i = 0; i < dataJSONArray.length(); i++) {
            JSONObject childJSONObject = dataJSONArray.getJSONObject(i);
            boolean isEmp = true;
            String studOremp = " ";
            try {
                studOremp = childJSONObject.getString("Employee");
            } catch (JSONException e) {
                studOremp = childJSONObject.getString("Personality");
                isEmp = false;

            }

            if (isEmp) {
                dataArray.add(new TimeTableData(studOremp, "",
                        childJSONObject.getString("Subject"), childJSONObject
                        .getString("Building"), childJSONObject
                        .getString("EmployeePhotoPath"),
                        childJSONObject.getInt("WeekNum"),
                        childJSONObject.getInt("DayId"), childJSONObject.getString("DayName"),
                        childJSONObject.getInt("LessonId")));
            } else {
                dataArray.add(new TimeTableData("", studOremp,
                        childJSONObject.getString("Subject"), childJSONObject
                        .getString("Building"), childJSONObject
                        .getString("EmployeePhotoPath"),
                        childJSONObject.getInt("WeekNum"),
                        childJSONObject.getInt("DayId"), childJSONObject.getString("DayName"),
                        childJSONObject.getInt("LessonId")));
            }
        }
        return dataArray;
    }
}
