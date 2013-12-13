package ua.kpi.campus.api.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Timestamp;

/**
 * @author Serhii Hokhalenko
 * @author Artur Dzidzoiev
 * @version 04 Dec 2013
 */
public class JSONAuthorizationParser {
    private static final String STATUS_CODE_ATTRIBUTE_NAME = "StatusCode";
    private static final String TIMESTAMP_ATTRIBUTE_NAME = "TimeStamp";
    private static final String GUID_ATTRIBUTE_NAME = "Guid";
    private static final String PAGING_ATTRIBUTE_NAME = "Paging";
    private static final String DATA_ATTRIBUTE_NAME = "Data";

    /**
     * @param jsonString String from JSON file
     * @return Authorization object with all JSON fields
     */
    public static JsonObject<String> parse(String jsonString) throws JSONException{
            JSONObject authorization = new JSONObject(jsonString);
            String timeStampString = authorization.getString(
                    TIMESTAMP_ATTRIBUTE_NAME).replace('T', ' ');
            timeStampString = timeStampString.substring(0,
                    timeStampString.length() - 6);

            return new JsonObject<String>(
                    authorization.getInt(STATUS_CODE_ATTRIBUTE_NAME),
                    Timestamp.valueOf(timeStampString),
                    authorization.getString(GUID_ATTRIBUTE_NAME),
                    authorization.getString(PAGING_ATTRIBUTE_NAME),
                    authorization.getString(DATA_ATTRIBUTE_NAME));
    }
}
