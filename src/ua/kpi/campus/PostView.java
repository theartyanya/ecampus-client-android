package ua.kpi.campus;

import java.util.ArrayList;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dny.android.Activity;
import dny.android.util.Listener;
import dny.android.widgets.CardView;
import ua.kpi.campus.Campus.*;
import java.io.*;

public class PostView extends CardView {
	
	public PostView(
		final Post post, 
		final Activity activity, 
		final ArrayList<Post> posts,
		final Runnable refreshAction
	) {
		super(activity);
		
		setLayoutParams(new LayoutParams(
			LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT
		));
		
		linearLayoutSetting: {
			
			LinearLayout layout = new LinearLayout(activity);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
			));
			layout.setBackgroundColor(0xffffffff);
			
			titleSetting: {
				
				LinearLayout title = new LinearLayout(activity);
				title.setOrientation(LinearLayout.HORIZONTAL);
				title.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				title.setBackgroundColor(0xffcccccc);
				
				subjectSetting: {
					TextView subject = new TextView(activity);
					final int padding = (int)(Campus.density * 4);
					subject.setPadding(padding, padding, padding, padding);
					subject.setTextSize(16);
					subject.setTextColor(0xff000000);
					subject.setText(post.subject);
					title.addView(subject);
				}
				
				layout.addView(title);
				
			}
			
			bodySetting: {
				
				RelativeLayout body = new RelativeLayout(activity);
				body.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT
				));
				
				textSetting: {
					TextView text = new TextView(activity);
					final int padding = (int)(Campus.density * 8);
					text.setPadding(padding, padding, padding, padding);
					text.setTextColor(0xff000000);
					text.setText(post.text);
					body.addView(text);
				}
				
				layout.addView(body);
				
			}

			toolbarSetting: {

				LinearLayout toolbar = new LinearLayout(activity);
				toolbar.setOrientation(LinearLayout.HORIZONTAL);
				toolbar.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				toolbar.setBackgroundColor(0xffcccccc);
				
				if (post.editable) editButtonsSetting: {
					
					Button editButton = new Button(activity);
					editButton.setText("Редактировать");
					editButton.setOnClickListener(new Listener(new Runnable() {@Override public void run() {
						activity.open(EditActivity.class, post, refreshAction);
					}}));
					toolbar.addView(editButton);

					Button deleteButton = new Button(activity);
					deleteButton.setText("Удалить");
					deleteButton.setOnClickListener(new Listener(new Runnable() {@Override public void run() {
						try {
							Campus.deletePost(post);
							posts.remove(post);
							refreshAction.run();
						} catch (Campus.AccessException e) {
							Campus.showToast(getResources().getString(R.string.access_error));
						} catch (IOException e) {
							Campus.showToast(getResources().getString(R.string.connection_error));
						}
					}}));
					toolbar.addView(deleteButton);
					
				}
				
				authorSetting: {
					TextView author = new TextView(activity);
					final int padding = (int)(Campus.density * 4);
					author.setPadding(padding, padding, padding, padding);
					author.setTextColor(0x88000000);
					author.setText(post.author);
					toolbar.addView(author);
				}

				dateSetting: {
					TextView date = new TextView(activity);
					final int padding = (int)(Campus.density * 4);
					date.setPadding(padding, padding, padding, padding);
					date.setTextColor(0x88000000);
					date.setText(post.modified.toString());
					toolbar.addView(date);
				}

				layout.addView(toolbar);

			}
			
			addView(layout);
			
		}
		
	}
	
}
