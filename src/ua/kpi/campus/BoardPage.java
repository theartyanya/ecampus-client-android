package ua.kpi.campus;

import java.io.IOException;
import java.util.ArrayList;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.view.ViewGroup;

import dny.android.Activity;
import dny.parallel.*;

public class BoardPage extends Page {
	
	private final Activity activity;

	@Override public String getTitle() {
		return getResources().getString(R.string.board_page_title);
	}

	private final ArrayList<Post> posts = new ArrayList<Post>();
	private final LinearLayout listView;
	
	public Filter filter;

	public void refreshPage() {
		listView.removeAllViews();
		for (Post post : posts) {
			if (!filter.fits(post)) continue;
			listView.addView(new PostView(post, activity, this));
		}
	}
	
	public void addPost(Post post) {
		posts.add(post);
		refreshPage();
	}
	
	public void removePost(Post post) {
		posts.remove(post);
		refreshPage();
	}
	
	private void loadPosts() {
		new Run(new Runnable() {@Override public void run() {
			try {
				ArrayList<Post> newPosts = CampusAPI.getPosts();
				for (Post post : newPosts) {
					posts.add(post);
				}
				activity.runOnUiThread(new Runnable() {@Override public void run() {
					refreshPage();
				}});
			} catch (IOException e) {
				ThisApp.showToast(getResources().getString(R.string.connection_error));
			} catch (CampusAPI.AccessException e) {
				ThisApp.showToast(getResources().getString(R.string.access_error));
			}
		}}).open();
	}
	
	public BoardPage(Activity activity, Filter filter) {
		super(activity);
		this.activity = activity;
		this.filter = filter;
		
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
