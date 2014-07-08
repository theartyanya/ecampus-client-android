package ua.kpi.campus;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.app.Application;
import android.os.Handler;
import android.widget.Toast;

public class Campus extends Application {
	
	private static Campus instance;
	
	public static float density;
	
	private static Handler handler = new Handler();
	public static void showToast(final String s) {
		handler.post(new Runnable() {@Override public void run() {
			Toast.makeText(instance, s, Toast.LENGTH_LONG).show();
		}});
	}
	
	public static User user;
	public static class AuthException extends Exception {}
	public static void auth(String login, String password) throws IOException, AuthException {
		// Заглушка //////////////////////////////////////////////////////////////////////////////////////////
		if (!login.equals("user") || !password.equals("pass")) throw new AuthException();
		user = new User(true);
		// Заглушка //////////////////////////////////////////////////////////////////////////////////////////
	}
	
	public static class AccessException extends Exception {}
	
	public static Post[] getPosts() throws IOException {
		// Заглушка //////////////////////////////////////////////////////////////////////////////////////////
		return new Post[] {};
		// Заглушка //////////////////////////////////////////////////////////////////////////////////////////
	}
	
	public static boolean postPost(Post post) throws IOException, AccessException {
		return false;
	}
	
	public static void deletePost(Post post) throws IOException, AccessException {
		
	}

	@Override public void onCreate() {
		super.onCreate();
		instance = this;
		density = getResources().getDisplayMetrics().density;
	}
	
}
