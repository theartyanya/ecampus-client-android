package ua.kpi.campus.api.jsonparsers;

import java.sql.Timestamp;

/**
 * General class for any JSON-object
 *
 * @author Artur Dzidzoiev
 * @version 13/12/13
 * @param <T> - data type
 */
public final class JsonObject<T> {
	private final int statusCode;
	private final Timestamp timeStamp;
	private final String guid;
	private final Object padding;
    private final T data;

	public JsonObject(int statusCode, Timestamp timeStamp, String guid,
                      Object padding, T data) {
		super();
		this.statusCode = statusCode;
		this.timeStamp = timeStamp;
		this.guid = guid;
		this.padding = padding;
		this.data = data;
	}

	public final int getStatusCode() {
		return statusCode;
	}

	public final Timestamp getTimeStamp() {
		return timeStamp;
	}

	public final String getGuid() {
		return guid;
    }

	public final Object getPadding() {
		return padding;
	}

	public final T getData() {
		return data;
	}
}
