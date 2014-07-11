package ua.kpi.campus;

import java.util.Calendar;
import java.util.Date;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

// библиотеку DateTimePicker можно найти тут: https://github.com/dirtynewyorker/datetimepicker
import com.fourmob.datetimepicker.date.DatePickerDialog;

import dny.android.Activity;
import dny.android.util.ListenerAdapter;

public class DatePickerActivity extends Activity {
	
	private Date date;
	private Runnable submitAction;

	@Override protected void onPause() {
		super.onPause();
		submitAction.run();
	}

	@Override protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		date = (Date)arg1;
		submitAction = (Runnable)arg2;

		final int padding = (int)(Campus.density * 16);
		
		layoutSetting: {
			
			final LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
			));
			layout.setPadding(padding, padding, padding, padding);
			
			relativeLayoutSetting: {
				
				final RelativeLayout relativelayout = new RelativeLayout(this);
				int relativelayoutId = View.generateViewId();
				relativelayout.setId(relativelayoutId);
				relativelayout.setLayoutParams(new LinearLayout.LayoutParams(
					(int)(Campus.density * 800),
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				relativelayout.setPadding(padding, padding, padding, padding);
				
				datePickerSetting: {
					int year, month, day;
					if (date.getTime() == 0) {
						Date now = Calendar.getInstance().getTime();
						year = now.getYear();
						month = now.getMonth();
						day = now.getDate();
					} else {
						year = date.getYear();
						month = date.getMonth();
						day = date.getDate();
					}
					final DatePickerDialog datePicker = DatePickerDialog.newInstance(
						new DatePickerDialog.OnDateSetListener() {@Override public void onDateSet(
							DatePickerDialog datePickerDialog, 
							int year, int month, int day
						) {
							date.setYear(year - 1900);
							date.setMonth(month);
							date.setDate(day);
							submitAction.run();
							finish();
						}},
						year + 1900, month, day
					);
					FragmentManager fragmentManager = getFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					fragmentTransaction.add(relativelayoutId, datePicker);
					fragmentTransaction.commit();
				}

				layout.addView(relativelayout);
				
			}
			
			buttonSetting: {
				final Button button = new Button(this);
				button.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				button.setText("Любая");
				button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
					date.setTime(0);
					finish();
				}}));
				layout.addView(button);
			}

			setContentView(layout);
			
		}
		
	}
	
}
