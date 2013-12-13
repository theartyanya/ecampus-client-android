package ua.kpi.campus.api.jsonparsers.user;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @author Artur Dzidzoiev
 * @version 04 Dec 2013
 */
public final class Permissions {
	private final int statusCode;
    private final Timestamp timeStamp;
    private final String guid;
    private final Object padding;
	private final ArrayList<SubsystemData> data;
	
	
	public Permissions(int statusCode, Timestamp timeStamp, String guid,
                       Object padding, ArrayList<SubsystemData> data) {
		this.statusCode = statusCode;
		this.timeStamp = timeStamp;
		this.guid = guid;
		this.padding = padding;
		this.data = data;
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

	public ArrayList<SubsystemData> getData() {
		return data;
	}
}
