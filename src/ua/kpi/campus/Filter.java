package ua.kpi.campus;

import java.util.Date;

public class Filter {
	
	public Date from, to;
	
	public Subdivision subdivision;
	public Group group;
	public Profile profile;
	
	public boolean fits(final Post post) {
		
		dateCheck: {
			if (from != null) if (post.modified.before(from)) return false;
			if (to != null) if (post.modified.after(to)) return false;
		}
		
		subdivisionCheck: {
			if (subdivision == null) break subdivisionCheck;
			if (post.subdivision != subdivision) return false;
		}

		groupCheck: {
			if (group == null) break groupCheck;
			if (post.group != group) return false;
		}

		profileCheck: {
			if (profile == null) break profileCheck;
			if (post.profile != profile) return false;
		}
		
		return true;
		
	}
	
}
