package ua.kpi.campus;

import java.util.Date;

public class Filter extends AccessSettings {
	
	public final Date from = new Date(0);
	public final Date to = new Date(0);
	
	public boolean fits(final Post post) {
		
		dateCheck: {
			if (from.getTime() != 0) if (post.modified.before(from)) return false;
			if (to.getTime() != 0) if (post.modified.after(to)) return false;
		}
		
		subdivCheck: {
			if (subdiv == null) break subdivCheck;
			if (post.subdiv != subdiv) return false;
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
