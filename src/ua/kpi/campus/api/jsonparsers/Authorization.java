package ua.kpi.campus.api.jsonparsers;

import java.sql.Timestamp;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 24 Nov 2013
 */
public final class Authorization {
	private final int statusCode;
	private final Timestamp timeStamp;
	private final String guid;
	private final Object padding;
	private final String data;

	public Authorization(int statusCode, Timestamp timeStamp, String guid,
			Object padding, String data) {
		super();
		this.statusCode = statusCode;
		this.timeStamp = timeStamp;
		this.guid = guid;
		this.padding = padding;
		this.data = data;
	}

    @Override
    public String toString() {
        return "Authorization [statusCode=" + statusCode + ", timeStamp="
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

	public String getData() {
		return data;
	}
}
