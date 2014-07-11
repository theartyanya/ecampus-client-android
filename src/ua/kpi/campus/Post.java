package ua.kpi.campus;

import java.util.Calendar;
import java.util.Date;

public class Post extends AccessSettings {
	
	public int id = -1;
	
	public String subject;
	public String body;
	
	public String author;
	public Date created, modified;
	
	public boolean editable;
	
	public Post() {
		author = Campus.user.name;
		Date now = Calendar.getInstance().getTime();
		created = now; modified = now;
		editable = true;
	}
	
}
