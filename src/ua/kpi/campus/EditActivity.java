package ua.kpi.campus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import dny.android.Activity;
import dny.android.util.Listener;

public class EditActivity extends Activity {
	
	private Post post;
	private BoardPage board;

	@Override protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		post = (Post)arg1;
		board = (BoardPage)arg2;

		final int padding = (int)(Campus.density * 16);
		
		layoutSetting: {
			
			final LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
			));
			layout.setPadding(padding, padding, padding, padding);

			final EditText subject = new EditText(this);
			subjectSetting: {
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				);
				subject.setLayoutParams(layoutParams);
				subject.setPadding(padding, padding, padding, padding);
				subject.setTextSize(20);
				subject.setHint(getResources().getString(R.string.subject_hint));
				layout.addView(subject);
			}

			final EditText body = new EditText(this);
			bodySetting: {
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				);
				body.setLayoutParams(layoutParams);
				body.setPadding(padding, padding, padding, padding);
				body.setTextSize(16);
				body.setHint(getResources().getString(R.string.post_body_hint));
				layout.addView(body);
			}
			
			if (post != null) {
				subject.setText(post.subject);
				body.setText(post.body);
			}
			
			submitButtonSetting: {
				final Button submitButton = new Button(this);
				submitButton.setLayoutParams(new LinearLayout.LayoutParams(
					(int)(Campus.density * 512),
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				submitButton.setPadding(padding, padding, padding, padding);
				submitButton.setText(R.string.submit_button);
				submitButton.setOnClickListener(new Listener(new Runnable() {@Override public void run() {
					try {
						Post newPost = new Post(
							post,
							subject.getText().toString(),
							body.getText().toString()
						);
						Campus.postPost(newPost);
						if (post != null) {
							post.subject = newPost.subject;
							post.body = newPost.body;
							post.modified = newPost.modified;
							board.refreshPage();
						} else {
							board.addPost(newPost);
						}
						finish();
					} catch (IOException e) {
						Campus.showToast(getResources().getString(R.string.connection_error));
					} catch (Campus.AccessException e) {
						Campus.showToast(getResources().getString(R.string.access_error));
					}
				}}));
				layout.addView(submitButton);
			}
			
			setContentView(layout);
			
		}
		
	}
	
}
