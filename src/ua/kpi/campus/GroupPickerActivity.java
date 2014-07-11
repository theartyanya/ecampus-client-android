package ua.kpi.campus;

import java.io.IOException;
import java.util.ArrayList;

import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import dny.android.Activity;
import dny.android.util.ListenerAdapter;
import dny.util.Event;
import dny.util.Wrapper.MutableWrapper;
import dny.util.Wrapper.MutableWrapper.ConcreteMutableWrapper;

public class GroupPickerActivity extends Activity {

	private AccessSettings filter;
	private Runnable submitAction;

	@Override protected void onPause() {
		super.onPause();
		submitAction.run();
	}

	@Override protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		filter = (AccessSettings)arg1;
		submitAction = (Runnable)arg2;

		final ArrayList<Group> groups = new ArrayList<Group>();
		subdivsGetting: {
			try {
				groups.addAll(Campus.getGroups(filter.subdiv));
			} catch (IOException e) {
				Campus.showToast(getResources().getString(R.string.connection_error));
				finish();
			} catch (Campus.AccessException e) {
				Campus.showToast(getResources().getString(R.string.access_error));
				finish();
			}
		}

		final Event refreshEvent = new Event();
		final MutableWrapper<String> text = new ConcreteMutableWrapper<String>();

		layoutSetting: {

			final int padding = (int)(Campus.density * 16);

			final LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
			));
			layout.setPadding(padding, padding, padding, padding);

			titleSetting: {

				final LinearLayout title = new LinearLayout(this);
				title.setOrientation(LinearLayout.HORIZONTAL);
				title.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT
				));

				searchboxSetting: {

					final EditText searchbox = new EditText(this);
					searchbox.setInputType(InputType.TYPE_CLASS_TEXT);
					searchbox.setHint(getResources().getString(R.string.subdiv_hint));

					searchbox.addTextChangedListener(new ListenerAdapter(refreshEvent));

					refreshEvent.addAction(new Runnable() {@Override public void run() {
						text.setCore(searchbox.getText().toString());
					}});

					title.addView(searchbox);

				}

				anyButtonSetting: {

					final Button button = new Button(this);
					button.setLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT
					));
					button.setText("Любой");
					button.setOnClickListener(new ListenerAdapter(new Runnable() {@Override public void run() {
						filter.group = null;
						finish();
					}}));
					title.addView(button);

				}

				layout.addView(title);

			}

			scrollViewSetting: {

				final ScrollView scrollView = new ScrollView(this);

				listSetting: {

					final LinearLayout list = new LinearLayout(this);
					list.setOrientation(LinearLayout.VERTICAL);

					final int entryPadding = (int)(Campus.density * 16);

					refreshEvent.addAction(new Runnable() {@Override public void run() {
						list.removeAllViews();
						for (final Group group : groups) {
							if (!group.name.toLowerCase().contains(
								text.getCore().toLowerCase()
							)) continue;

							listEntrySetting: {

								final TextView entry = new TextView(GroupPickerActivity.this);
								entry.setPadding(entryPadding, entryPadding, entryPadding, 0);
								entry.setText(group.name);

								entry.setClickable(true);
								entry.setOnClickListener(new ListenerAdapter(new Runnable() {
								@Override public void run() {
									filter.group = group;
									finish();
								}}));

								list.addView(entry);

							}

						}
					}});

					scrollView.addView(list);

				}

				layout.addView(scrollView);

			}

			setContentView(layout);
			refreshEvent.run();

		}

	}

}
