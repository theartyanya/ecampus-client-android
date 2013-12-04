package ua.kpi.campus.api.jsonparsers;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 24 Nov 2013
 */
public  class Permissions {
	private final int statusCode;
	private final Timestamp timeStamp;
	private final String guid;
	private final Object padding;
	private final ArrayList<PermissionsData> data;
	
	
	public Permissions(int statusCode, Timestamp timeStamp, String guid,
                       Object padding, ArrayList<PermissionsData> data) {
		this.statusCode = statusCode;
		this.timeStamp = timeStamp;
		this.guid = guid;
		this.padding = padding;
		this.data = data;
	}

    @Override
    public String toString() {
        return "GetPermissions [statusCode=" + statusCode + ", timeStamp="
                + timeStamp + ", guid=" + guid + ", padding=" + padding
                + ", data=" + data + "]";
    }

    public int getStatusCode() {
		return statusCode;
	}

    public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public String getGuid() {
		return guid;
	}

	public Object getPadding() {
		return padding;
	}

	public ArrayList<PermissionsData> getData() {
		return data;
	}
}
