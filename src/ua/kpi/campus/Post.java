package ua.kpi.campus;

import java.util.Calendar;
import java.util.Date;

public class Post {
	
	public String subject;
	public String body;
	
	public String author;
	public Date created, modified;
	
	public boolean editable;
	
	public Post(String subject, String body, String author, Date created, Date modified, boolean editable) {
		this.subject = subject;
		this.body = body;
		this.author = author;
		this.created = created; this.modified = modified;
		this.editable = editable;
	}
	
	public Post(Post oldPost, String subject, String body) {
		this.subject = subject;
		this.body = body;
		modified = Calendar.getInstance().getTime();
		if (oldPost != null) {
			author = oldPost.author;
			editable = oldPost.editable;
			created = oldPost.created;
		} else {
			author = Campus.user.name;
			editable = true;
			created = modified;
		}
	}
	
}
