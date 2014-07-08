package ua.kpi.campus;

import java.util.Date;

public class Post {
	
	public String subject;
	public String text;
	
	public String author;
	public Date created, modified;
	
	public boolean editable;
	
	public Post(String subject, String text, String author, Date created, Date modified, boolean editable) {
		this.subject = subject;
		this.text = text;
		this.author = author;
		this.created = created; this.modified = modified;
		this.editable = editable;
	}
	
}
