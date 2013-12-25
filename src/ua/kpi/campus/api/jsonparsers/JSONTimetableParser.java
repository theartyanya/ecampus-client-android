package ua.kpi.campus.api.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 17.12.13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class JSONTimetableParser {
    private static final String STATUS_CODE_ATTRIBUTE_NAME = "StatusCode";
    private static final String TIMESTAMP_ATTRIBUTE_NAME = "TimeStamp";
    private static final String GUID_ATTRIBUTE_NAME = "Guid";
    private static final String PAGING_ATTRIBUTE_NAME = "Paging";

    public static JsonObject<ArrayList<TimeTableData>> parse(String jsonString) throws JSONException {
        JSONObject jsonObj = new JSONObject(jsonString);
        String timeStampString = jsonObj.getString(
                TIMESTAMP_ATTRIBUTE_NAME).replace('T', ' ');
        timeStampString = timeStampString.substring(0,
                timeStampString.length() - 6);


         return new JsonObject<ArrayList<TimeTableData>>(
                jsonObj.getInt(STATUS_CODE_ATTRIBUTE_NAME),
                Timestamp.valueOf(timeStampString),
                jsonObj.getString(GUID_ATTRIBUTE_NAME),
                jsonObj.getString(PAGING_ATTRIBUTE_NAME),JSONArrayParsers.parseTimeTableData(jsonObj)
        );
    }
}
