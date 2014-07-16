package ua.kpi.campus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import dny.android.Activity;
import dny.android.util.ListenerAdapter;
import dny.util.Event;
import dny.parallel.*;

public class EditActivity extends Activity {
	
	private Post post;
	private BoardPage board;
	
	private boolean postIsNew = false;
	
	private final Event submitEvent = new Event();
	
	@Override protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		post = (Post)arg1;
		board = (BoardPage)arg2;
		
		if (post == null) {
			post = new Post();
			postIsNew = true;
		}

		final int padding = (int)(ThisApp.density * 16);
		
		layoutSetting: {
			
			final LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
			));
			layout.setPadding(padding, padding, padding, padding);

			subjectSetting: {
				
				final EditText subject = new EditText(this);
				subject.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				subject.setPadding(padding, padding, padding, padding);
				subject.setTextSize(20);
				subject.setHint(getResources().getString(R.string.subject_hint));
				
				subject.setText(post.subject);
				
				submitEvent.addAction(new Runnable() {@Override public void run() {
					post.subject = subject.getText().toString();
				}});

				layout.addView(subject);
				
			}

			bodySetting: {
				
				final EditText body = new EditText(this);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				);
				body.setLayoutParams(layoutParams);
				body.setPadding(padding, padding, padding, padding);
				body.setTextSize(16);
				body.setHint(getResources().getString(R.string.post_body_hint));
				
				body.setText(post.body);
				
				submitEvent.addAction(new Runnable() {@Override public void run() {
					post.body = body.getText().toString();
				}});

				layout.addView(body);
						
			}
			
			filterButtonSetting: {
				
				final Button button = new Button(this);
				button.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				button.setPadding(padding, padding, padding, padding);
				button.setText(R.string.access_settings);
				
				button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
					open(AccessSettingsActivity.class, post, null);
				}}));

				layout.addView(button);
				
			}
			
			submitButtonSetting: {
				
				final Button button = new Button(this);
				button.setLayoutParams(new LinearLayout.LayoutParams(
					(int)(ThisApp.density * 512),
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				button.setPadding(padding, padding, padding, padding);
				button.setText(R.string.submit_button);
				button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
					
					submitEvent.run();
					new Run(new Runnable() {@Override public void run() {
						try {
							CampusAPI.postPost(post);
							runOnUiThread(new Runnable() {@Override public void run() {
								if (postIsNew) board.addPost(post);
								else board.refreshPage();
								finish();
							}});
						} catch (IOException e) {
							ThisApp.showToast(getResources().getString(R.string.connection_error));
						} catch (CampusAPI.AccessException e) {
							ThisApp.showToast(getResources().getString(R.string.access_error));
						}
					}}).open();
				}}));
				
				layout.addView(button);
			}
			
			setContentView(layout);
			
		}
		
	}
	
}
