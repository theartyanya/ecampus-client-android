package ua.kpi.campus;

import dny.android.Activity;

public class EditActivity extends Activity {
	
	private Post post;
	private Runnable boardRefreshAction;

	@Override protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		post = (Post)arg1;
		boardRefreshAction = (Runnable)arg2;
		
		
		
	}
	
}
