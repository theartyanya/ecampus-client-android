package ua.kpi.campus;

import java.io.IOException;
import java.util.ArrayList;
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
		user = new User("user", true);
		// Заглушка //////////////////////////////////////////////////////////////////////////////////////////
	}
	
	public static class AccessException extends Exception {}
	
	public static ArrayList<Post> getPosts() throws IOException {
		// Заглушка //////////////////////////////////////////////////////////////////////////////////////////
		return new ArrayList<Post>();
		// Заглушка //////////////////////////////////////////////////////////////////////////////////////////
	}
	
	public static void postPost(Post post) throws IOException, AccessException {
		post.id = 333;
	}
	
	public static void deletePost(Post post) throws IOException, AccessException {
		
	}
	
	public static ArrayList<Subdiv> getSubdivs() throws IOException, AccessException {
		ArrayList<Subdiv> subdivs = new ArrayList<Subdiv>();
		subdivs.add(new Subdiv("ФИВТ"));
		subdivs.add(new Subdiv("ИПСА"));
		return subdivs;
	}
	
	public static ArrayList<Group> getGroups(Subdiv subdiv) throws IOException, AccessException {
		ArrayList<Group> groups = new ArrayList<Group>();
		groups.add(new Group("ИП-11"));
		groups.add(new Group("ИП-12"));
		return groups;
	}
	
	public static ArrayList<Profile> getProfiles() throws IOException, AccessException {
		ArrayList<Profile> profiles = new ArrayList<Profile>();
		profiles.add(new Profile("Студент"));
		profiles.add(new Profile("Профессор"));
		return profiles;
	}

	@Override public void onCreate() {
		super.onCreate();
		instance = this;
		density = getResources().getDisplayMetrics().density;
	}
	
}
