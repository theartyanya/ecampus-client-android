package ua.kpi.campus;

import java.util.Date;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import dny.android.Activity;
import dny.android.util.ListenerAdapter;
import dny.time.Time;
import dny.util.Event;

public class FilterActivity extends Activity {
	
	private Filter filter;
	private Runnable submitAction;

	@Override protected void onPause() {
		super.onPause();
		if (submitAction != null) submitAction.run();
	}

	@Override protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		filter = (Filter)arg1;
		submitAction = (Runnable)arg2;
		
		final Event refreshEvent = new Event();
		
		layoutSetting: {
			
			final LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
			));
			int padding = (int)(ThisApp.density * 16);
			layout.setPadding(padding, padding, padding, padding);
			
			dateSetting: {
				
				labelSetting: {
					final TextView label = new TextView(this);
					label.setTextSize(24);
					label.setTextColor(0xff000000);
					label.setText(getResources().getString(R.string.date_label));
					layout.addView(label);
				}
				
				dateLayoutSetting: {
					
					final LinearLayout dateLayout = new LinearLayout(this);
					dateLayout.setOrientation(LinearLayout.HORIZONTAL);
					
					fromLabelSetting: {
						final TextView label = new TextView(this);
						label.setTextSize(16);
						label.setTextColor(0xff000000);
						label.setText(getResources().getString(R.string.from_label));
						dateLayout.addView(label);
					}
					
					fromButtonSetting: {
						final Button button = new Button(this);
						final Date date = filter.from;
						button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
							open(
								DatePickerActivity.class, 
								date,
								refreshEvent
							);
						}}));
						refreshEvent.addAction(new Runnable() {@Override public void run() {
							if (date.getTime() == 0) {
								button.setText("--.--.----");
							} else {
								button.setText(Time.toString(date, true, false));
							}
						}});
						dateLayout.addView(button);
					}
					
					toLabelSetting: {
						final TextView label = new TextView(this);
						label.setTextSize(16);
						label.setTextColor(0xff000000);
						label.setText(getResources().getString(R.string.to_label));
						dateLayout.addView(label);
					}

					toButtonSetting: {
						final Button button = new Button(this);
						final Date date = filter.to;
						button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
							open(
								DatePickerActivity.class, 
								date,
								refreshEvent
							);
						}}));
						refreshEvent.addAction(new Runnable() {@Override public void run() {
							if (date.getTime() == 0) {
								button.setText("--.--.----");
							} else {
								button.setText(Time.toString(date, true, false));
							}
						}});
						dateLayout.addView(button);
					}
					
					layout.addView(dateLayout);
					
				}
				
			}

			subdivSetting: {

				labelSetting: {
					final TextView label = new TextView(this);
					label.setTextSize(24);
					label.setTextColor(0xff000000);
					label.setText(getResources().getString(R.string.subdiv_label));
					layout.addView(label);
				}

				buttonSetting: {
					final Button button = new Button(this);
					button.setLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT
					));
					button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
						open(SubdivPickerActivity.class, filter, refreshEvent);
					}}));
					refreshEvent.addAction(new Runnable() {@Override public void run() {
						Subdiv subdiv = filter.subdiv;
						if (subdiv == null) button.setText("Любой");
						else button.setText(subdiv.name);
					}});
					layout.addView(button);
				}

			}
			
			groupSetting: {
				
				final LinearLayout groupLayout = new LinearLayout(this);
				groupLayout.setOrientation(LinearLayout.HORIZONTAL);
				
				labelSetting: {
					final TextView label = new TextView(this);
					label.setTextSize(16);
					label.setTextColor(0xff000000);
					label.setText(getResources().getString(R.string.group_label));
					groupLayout.addView(label);
				}

				buttonSetting: {
					final Button button = new Button(this);
					button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
						open(GroupPickerActivity.class, filter, refreshEvent);
					}}));
					refreshEvent.addAction(new Runnable() {
						private Subdiv oldSubdiv;
						@Override public void run() {
							Subdiv subdiv = filter.subdiv;
							if (oldSubdiv != null) if (subdiv != oldSubdiv) filter.group = null;
							oldSubdiv = subdiv;
							Group group = filter.group;
							if (group == null) button.setText("Любая");
							else button.setText(group.name);
						}
					});
					groupLayout.addView(button);
				}
				
				refreshEvent.addAction(new Runnable() {@Override public void run() {
					if (filter.subdiv == null) {
						groupLayout.setVisibility(View.GONE);
					} else {
						groupLayout.setVisibility(View.VISIBLE);
					}
				}});
				
				layout.addView(groupLayout);
				
			}
			
			profileSetting: {
				
				labelSetting: {
					final TextView label = new TextView(this);
					label.setTextSize(24);
					label.setTextColor(0xff000000);
					label.setText(getResources().getString(R.string.profile_label));
					layout.addView(label);
				}

				buttonSetting: {
					final Button button = new Button(this);
					button.setLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT
					));
					button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
						open(ProfilePickerActivity.class, filter, refreshEvent);
					}}));
					refreshEvent.addAction(new Runnable() {@Override public void run() {
						Profile profile = filter.profile;
						if (profile == null) button.setText("Любой");
						else button.setText(profile.name);
					}});
					layout.addView(button);
				}
				
			}
			
			resetButtonSetting: {
				
				final Button button = new Button(this);
				button.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				button.setText(getResources().getString(R.string.reset_filter));
				
				button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
					filter.from.setTime(0);
					filter.to.setTime(0);
					filter.subdiv = null;
					filter.group = null;
					filter.profile = null;
					refreshEvent.run();
				}}));
				
				layout.addView(button);
				
			}
			
			setContentView(layout);
			refreshEvent.run();
			
		}
		
	}
	
}
