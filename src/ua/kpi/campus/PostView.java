package ua.kpi.campus;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dny.android.Activity;
import dny.android.util.Listener;
import dny.android.widgets.CardView;
import dny.android.widgets.FlatButton;
import dny.time.Time;

public class PostView extends CardView {
	
	public PostView(
		final Post post, 
		final Activity activity,
		final BoardPage board
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
				if (post.editable) {
					title.setBackgroundColor(0xffcc4444);
				} else {
					title.setBackgroundColor(0x11000000);
				}
				
				subjectSetting: {
					TextView subject = new TextView(activity);
					final int padding = (int)(Campus.density * 6);
					subject.setPadding(padding, padding, padding, padding);
					subject.setTextSize(24);
					if (post.editable) {
						subject.setTextColor(0xffffffff);
					} else {
						subject.setTextColor(0xff000000);
					}
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
					text.setText(post.body);
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
				toolbar.setGravity(Gravity.CENTER_VERTICAL);
				toolbar.setBackgroundColor(0x11000000);
				
				if (post.editable) editButtonsSetting: {
					
					FlatButton editButton = new FlatButton(
						BitmapFactory.decodeResource(getResources(), R.drawable.edit),
						new Runnable() {@Override public void run() {
							activity.open(EditActivity.class, post, board);
						}},
						activity
					);
					toolbar.addView(editButton);
					
					FlatButton deleteButton = new FlatButton(
						BitmapFactory.decodeResource(getResources(), R.drawable.remove),
						new Runnable() {@Override public void run() {
							try {
								Campus.deletePost(post);
								board.removePost(post);
							} catch (Campus.AccessException e) {
								Campus.showToast(getResources().getString(R.string.access_error));
							} catch (IOException e) {
								Campus.showToast(getResources().getString(R.string.connection_error));
							}
						}},
						activity
					);
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
					date.setText(Time.toString(post.modified));
					toolbar.addView(date);
				}

				layout.addView(toolbar);

			}
			
			addView(layout);
			
		}
		
	}
	
}
