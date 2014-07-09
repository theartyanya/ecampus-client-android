package ua.kpi.campus;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import dny.android.Activity;

public class FilterActivity extends Activity {
	
	private BoardPage boardPage;
	private Filter filter;

	@Override protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boardPage = (BoardPage)arg1;
		filter = boardPage.filter;
		
		layoutSetting: {
			
			final LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
			));
			int padding = (int)(Campus.density * 16);
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
					groupLayout.addView(button);
				}
				
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
					layout.addView(button);
				}
				
			}
			
			setContentView(layout);
			
		}
		
	}
	
}
