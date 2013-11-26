package ua.kpi.campus.jsonparsers;

import java.sql.Timestamp;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 24 Nov 2013
 */
public class Authorization {
	@Override
	public String toString() {
		return "Authorization [statusCode=" + statusCode + ", timeStamp="
				+ timeStamp + ", guid=" + guid + ", padding=" + padding
				+ ", data=" + data + "]";
	}

	private int statusCode;
	private Timestamp timeStamp;
	private String guid;
	private Object padding;
	private String data;

	public Authorization() {
	}

	public Authorization(int statusCode, Timestamp timeStamp, String guid,
			Object padding, String data) {
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

	String getData() {
		return data;
	}

	void setData(String data) {
		this.data = data;
	}

}
