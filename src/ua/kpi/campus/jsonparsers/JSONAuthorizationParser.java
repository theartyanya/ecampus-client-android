package ua.kpi.campus.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;
import ua.kpi.campus.session.Authorization;

import java.sql.Timestamp;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 24 Nov 2013
 */
public class JSONAuthorizationParser {
	private static final String STATUS_CODE_ATTRIBUTE_NAME = "StatusCode";
	private static final String TIMESTAMP_ATTRIBUTE_NAME = "TimeStamp";
	private static final String GUID_ATTRIBUTE_NAME = "Guid";
	private static final String PAGING_ATTRIBUTE_NAME = "Paging";
	private static final String DATA_ATTRIBUTE_NAME = "Data";

	/**
	 * 
	 * @param jsonString
	 *            String from JSON file
	 * @return Authorization object with all JSON fields
	 */
	public static Authorization parse(String jsonString) {

		try {
			JSONObject authorization = new JSONObject(jsonString);
			// reduction the Timestamp String format

			String timeStampString = authorization.getString(
					TIMESTAMP_ATTRIBUTE_NAME).replace('T', ' ');
			timeStampString = timeStampString.substring(0,
					timeStampString.length() - 6);

			return new Authorization(
					authorization.getInt(STATUS_CODE_ATTRIBUTE_NAME),
					Timestamp.valueOf(timeStampString),
					authorization.getString(GUID_ATTRIBUTE_NAME),
					(Object) authorization.getString(PAGING_ATTRIBUTE_NAME),
					authorization.getString(DATA_ATTRIBUTE_NAME));

		} catch (JSONException exeption) {
			System.out.println(exeption.toString());
		}
		return null;
	}
}
