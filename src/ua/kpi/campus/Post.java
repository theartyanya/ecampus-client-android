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
		author = CampusAPI.user.name;
		Date now = Calendar.getInstance().getTime();
		created = now; modified = now;
		editable = true;
	}
	
	public Post(
		int id,
		String subject,
		String body,
		String author,
		Date created,
		Date modified,
		boolean editable
	) {
		this.id = id;
		this.subject = subject;
		this.body = body;
		this.author = author;
		this.created = created;
		this.modified = modified;
		this.editable = editable;
	}
	
}
