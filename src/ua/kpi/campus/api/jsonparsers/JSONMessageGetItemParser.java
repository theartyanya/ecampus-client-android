package ua.kpi.campus.api.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;
import ua.kpi.campus.api.jsonparsers.message.MessageGetItemData;
import ua.kpi.campus.api.jsonparsers.message.Padding;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 17.12.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class JSONMessageGetItemParser {
    private static final String STATUS_CODE_ATTRIBUTE_NAME = "StatusCode";
    private static final String TIMESTAMP_ATTRIBUTE_NAME = "TimeStamp";
    private static final String GUID_ATTRIBUTE_NAME = "Guid";
    private static final String PAGING_ATTRIBUTE_NAME = "Paging";

    public static JsonObject<ArrayList<MessageGetItemData>> parse(String jsonString) throws JSONException {
        JSONObject jsonObj = new JSONObject(jsonString);
        String timeStampString = jsonObj.getString(
                TIMESTAMP_ATTRIBUTE_NAME).replace('T', ' ');
        timeStampString = timeStampString.substring(0,
                timeStampString.length() - 6);
        JSONObject JSONPadding=jsonObj.getJSONObject("Padding");
        Padding padding=new Padding(JSONPadding.getInt("PageCount"),JSONPadding.getInt("TotalItemCount"),
                JSONPadding.getInt("PageNumber"),JSONPadding.getInt("PageSize"),
                JSONPadding.getBoolean("HasPreviousPage"),JSONPadding.getBoolean("HasNextPage"),
                JSONPadding.getBoolean("IsFirstPage"),JSONPadding.getBoolean("IsLastPage"),
                JSONPadding.getInt("FirstItemOnPage"),JSONPadding.getInt("LastItemOnPage"));

        return new JsonObject<ArrayList<MessageGetItemData>>(
                jsonObj.getInt(STATUS_CODE_ATTRIBUTE_NAME),
                Timestamp.valueOf(timeStampString),
                jsonObj.getString(GUID_ATTRIBUTE_NAME),
                padding,JSONArrayParsers.parseMessageGetItem(jsonObj)
        );
    }
}
