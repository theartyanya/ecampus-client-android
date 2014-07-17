package ua.kpi.campus;

public class User {
	
	public final String name;
	public final String sessionId;
	public final boolean moder;
	
	public User(String name, String sessionId, boolean moder) {
		this.name = name;
		this.sessionId = sessionId;
		this.moder = moder;
	}
	
}
