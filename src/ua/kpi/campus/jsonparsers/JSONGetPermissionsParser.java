package ua.kpi.campus.jsonparsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 24  2013
 */
public class JSONGetPermissionsParser {
	private static final String STATUS_CODE_ATTRIBUTE_NAME = "StatusCode";
	private static final String TIMESTAMP_ATTRIBUTE_NAME = "TimeStamp";
	private static final String GUID_ATTRIBUTE_NAME = "Guid";
	private static final String PAGING_ATTRIBUTE_NAME = "Paging";

	public static GetPermissions parse(String jsonString) {

		try {
			JSONObject getPermissionsObj = new JSONObject(jsonString);
			System.out.println(getPermissionsObj
					.getString(TIMESTAMP_ATTRIBUTE_NAME));
			String timeStampString = getPermissionsObj.getString(
					TIMESTAMP_ATTRIBUTE_NAME).replace('T', ' ');
			timeStampString = timeStampString.substring(0,
					timeStampString.length() - 6);
			// create data array
			
			JSONArray dataJSONArray = new JSONArray(
					getPermissionsObj.getJSONArray("Data"));
			ArrayList<GetPermissionsData> dataArray = new ArrayList<GetPermissionsData>();

			try{
			for (int i = 0; i < dataJSONArray.length(); i++) {
				JSONObject childJSONObject = dataJSONArray.getJSONObject(i);
				dataArray.add(new GetPermissionsData(getPermissionsObj
						.getString("SubsystemName"), getPermissionsObj
						.getBoolean("IsCreate"), getPermissionsObj
						.getBoolean("IsRead"), getPermissionsObj
						.getBoolean("IsUpdate"), getPermissionsObj
						.getBoolean("IsDelete")));
				
			}
			}			catch(NullPointerException e){System.out.println("nullpoint");}
			
			
			
			return new GetPermissions(
					getPermissionsObj.getInt(STATUS_CODE_ATTRIBUTE_NAME),
					Timestamp.valueOf(timeStampString),
					getPermissionsObj.getString(GUID_ATTRIBUTE_NAME),
					(Object) getPermissionsObj.getString(PAGING_ATTRIBUTE_NAME),
					dataArray);

		} catch (JSONException exeption) {
			System.out.println(exeption.toString());
		}
		
		return null;
	}
}
