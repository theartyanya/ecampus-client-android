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
import dny.android.util.Listener;

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
		
		final int padding = (int)(Campus.density * 16);
		
		linearLayoutSetting: {
			
			final LinearLayout linearLayout = new LinearLayout(this);
			linearLayout.setOrientation(LinearLayout.VERTICAL);
			linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
			));

			final EditText loginField = new EditText(this);
			loginFieldSetting: {
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				);
				loginField.setLayoutParams(layoutParams);
				loginField.setPadding(padding, padding, padding, padding);
				loginField.setInputType(InputType.TYPE_CLASS_TEXT);
				loginField.setHint(R.string.login_hint);
				linearLayout.addView(loginField);
			}

			final EditText passwordField = new EditText(this);
			passwordFieldSetting: {
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
				);
				passwordField.setLayoutParams(layoutParams);
				passwordField.setPadding(padding, padding, padding, padding);
				passwordField.setInputType(
					InputType.TYPE_CLASS_TEXT | 
					InputType.TYPE_TEXT_VARIATION_PASSWORD
				);
				passwordField.setHint(R.string.password_hint);
				linearLayout.addView(passwordField);
			}
			
			final TextView messageView = new TextView(this);
			messageViewSetting: {
				messageView.setPadding(padding, padding, padding, padding);
				linearLayout.addView(messageView);
				setMessage(messageView, true, getResources().getString(R.string.please_auth));
			}
			
			submitButtonSetting: {
				final Button submitButton = new Button(this);
				submitButton.setLayoutParams(new LinearLayout.LayoutParams(
					(int)(Campus.density * 256),
					LinearLayout.LayoutParams.WRAP_CONTENT
				));
				submitButton.setPadding(padding, padding, padding, padding);
				submitButton.setText(R.string.submit_button);
				submitButton.setOnClickListener(new Listener(new Runnable() {@Override public void run() {
					try {
						setMessage(messageView, true, getResources().getString(R.string.auth_wait));
						Campus.auth(
							loginField.getText().toString(), 
							passwordField.getText().toString()
						);
						setMessage(messageView, true, getResources().getString(R.string.auth_okay));
						open(MainActivity.class);
						return;
					} catch (IOException e) {
						setMessage(messageView, false, getResources().getString(R.string.connection_error));
					} catch (Campus.AuthException e) {
						clearFields(loginField, passwordField);
						setMessage(messageView, false, getResources().getString(R.string.auth_error));
					}
				}}));
				linearLayout.addView(submitButton);
			}
			
			setContentView(linearLayout);
			
		}
		
	}
	
}
