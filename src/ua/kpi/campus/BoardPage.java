package ua.kpi.campus;

import java.io.IOException;
import java.util.ArrayList;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.view.ViewGroup;

import dny.android.Activity;

public class BoardPage extends Page {
	
	private final Activity activity;

	@Override public String getTitle() {
		return getResources().getString(R.string.board_page_title);
	}

	private final ArrayList<Post> posts = new ArrayList<Post>();
	private final LinearLayout listView;

	private void refreshPage() {
		listView.removeAllViews();
		for (Post post : posts) {
			listView.addView(new PostView(post, activity, posts, refreshAction));
		}
	}
	protected Runnable refreshAction = new Runnable() {@Override public void run() {
		refreshPage();
	}};
	
	private void loadPosts() {
		try {
			Post[] newPosts = Campus.getPosts();
			for (Post post : newPosts) {
				posts.add(post);
			}
			refreshPage();
		} catch (IOException e) {
			Campus.showToast(getResources().getString(R.string.connection_error));
		}
	}
	
	public BoardPage(Activity activity) {
		super(activity);
		this.activity = activity;
		
		scrollViewSetting: {
			
			final ScrollView scrollView = new ScrollView(activity);
			scrollView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT
			));
			
			listSetting: {
				listView = new LinearLayout(activity);
				listView.setOrientation(LinearLayout.VERTICAL);
				scrollView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT
				));
				scrollView.addView(listView);
			}

			addView(scrollView);
			
			loadPosts();
			
		}
		
	}
	
}
