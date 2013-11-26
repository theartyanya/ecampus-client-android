package ua.kpi.campus.jsonparsers;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 24 Nov 2013
 */
class GetPermissions {
	private int statusCode;
	private Timestamp timeStamp;
	private String guid;
	private Object padding;
	private ArrayList<GetPermissionsData> data;
	
	
	public GetPermissions(int statusCode, Timestamp timeStamp, String guid,
			Object padding, ArrayList<GetPermissionsData> data) {
		super();
		this.statusCode = statusCode;
		this.timeStamp = timeStamp;
		this.guid = guid;
		this.padding = padding;
		this.data = data;
	}
	int getStatusCode() {
		return statusCode;
	}
	void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	@Override
	public String toString() {
		return "GetPermissions [statusCode=" + statusCode + ", timeStamp="
				+ timeStamp + ", guid=" + guid + ", padding=" + padding
				+ ", data=" + data + "]";
	}
	Timestamp getTimeStamp() {
		return timeStamp;
	}
	void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	String getGuid() {
		return guid;
	}
	void setGuid(String guid) {
		this.guid = guid;
	}
	Object getPadding() {
		return padding;
	}
	void setPadding(Object padding) {
		this.padding = padding;
	}
	ArrayList<GetPermissionsData> getData() {
		return data;
	}
	void setData(ArrayList<GetPermissionsData> data) {
		this.data = data;
	}
	
}
