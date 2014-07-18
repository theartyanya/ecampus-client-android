package ua.kpi.campus;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;

import dny.text.util.Streams;

public class CampusAPI {

	public static User user;
	public static class AuthException extends Exception {}
	public static void auth(final String login, final String password) throws IOException, AuthException {
        //TODO: add proxy setting to program
        /*
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kbis_user", "kbis13".toCharArray());
            }
        };
        Authenticator.setDefault(authenticator);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.13.100.13", 3128));
        */
		HttpURLConnection connection = (HttpURLConnection) new URL(
			"http://api.ecampus.kpi.ua/user/auth?" +
		 	"login=" + login + "&" +
		 	"password=" + password
		).openConnection();//(proxy)
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		if (connection.getResponseCode() == 403) throw new AuthException();
		InputStream inputStream = connection.getInputStream();
		String json = Streams.streamToString(inputStream);
		connection.disconnect();
		
		String sessionId;
		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser.parse(json);
			sessionId = (String) obj.get("Data");
			ThisApp.showToast(sessionId);
		} catch (ParseException e) {
			ThisApp.showToast("Parse exception");
			throw new IOException();
		}
		
		user = new User(
			login, 
			sessionId,
			true
		);
		
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
	
	
}
