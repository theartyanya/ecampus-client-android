package ua.kpi.campus;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup;

import dny.android.Activity;
import dny.android.util.ListenerAdapter;
import dny.parallel.Run;

public class LoginActivity extends Activity {
	
	private void setMessage(TextView messageView, boolean okay, String string) {
		if (okay) messageView.setTextColor(0xff000000);
		else messageView.setTextColor(0xffff0000);
		messageView.setText(string);
	}
	private void clearFields(final EditText loginField, final EditText passwordField) {
		loginField.setText("");
		passwordField.setText("");
	}

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final int padding = (int)(ThisApp.density * 16);
		
		layoutSetting: {
			
			final LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
			));
			layout.setPadding(padding, padding, padding, padding);

			final EditText login = new EditText(this);
			loginSetting: {
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				);
				login.setLayoutParams(layoutParams);
				login.setPadding(padding, padding, padding, padding);
				login.setInputType(InputType.TYPE_CLASS_TEXT);
				login.setHint(R.string.login_hint);
				layout.addView(login);
			}

			final EditText password = new EditText(this);
			passwordSetting: {
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				);
				password.setLayoutParams(layoutParams);
				password.setPadding(padding, padding, padding, padding);
				password.setInputType(
					InputType.TYPE_CLASS_TEXT | 
					InputType.TYPE_TEXT_VARIATION_PASSWORD
				);
				password.setHint(R.string.password_hint);
				layout.addView(password);
			}
			
			final TextView message = new TextView(this);
			messageSetting: {
				message.setPadding(padding, padding, padding, padding);
				layout.addView(message);
				setMessage(message, true, getResources().getString(R.string.please_auth));
			}
			
			submitButtonSetting: {
				final Button submitButton = new Button(this);
				submitButton.setLayoutParams(new LinearLayout.LayoutParams(
					(int)(ThisApp.density * 256),
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				submitButton.setPadding(padding, padding, padding, padding);
				submitButton.setText(R.string.auth_button);
				submitButton.setOnClickListener(new ListenerAdapter(new Runnable() {
				@Override public void run() {
					setMessage(message, true, getResources().getString(R.string.auth_wait));
					new Run(new Runnable() {@Override public void run() {
						try {
							CampusAPI.auth(
								login.getText().toString(), 
								password.getText().toString()
							);
							runOnUiThread(new Runnable() {@Override public void run() {
								setMessage(message, true, getResources().getString(R.string.auth_okay));
								open(MainActivity.class);
							}});
						} catch (IOException e) {
							runOnUiThread(new Runnable() {@Override public void run() {
								setMessage(message, false, getResources().getString(R.string.connection_error));
							}});
						} catch (CampusAPI.AuthException e) {
							runOnUiThread(new Runnable() {@Override public void run() {
								clearFields(login, password);
								setMessage(message, false, getResources().getString(R.string.auth_error));
							}});
						}
					}}).open();
				}}));
				layout.addView(submitButton);
			}
			
			setContentView(layout);
			
		}
		
	}
	
}
