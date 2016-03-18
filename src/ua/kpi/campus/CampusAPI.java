package ua.kpi.campus;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dny.text.util.Streams;

import ua.kpi.campus.util.DateParser;
import android.util.*;

public class CampusAPI {

	public static User user;
	public static class AuthException extends Exception {}
	public static void auth(final String login, final String password) throws IOException, AuthException {

		String sessionId;
		sessionIdObtaining: {
			HttpURLConnection connection = (HttpURLConnection) new URL(
				"http://api.ecampus.kpi.ua/user/Auth?" +
				"login=" + login + "&" +
				"password=" + password
			).openConnection();
			//connection.setRequestProperty("Accept-Charset", "UTF-8");
			if (connection.getResponseCode() == 403) throw new AuthException();
			InputStream inputStream = connection.getInputStream();
			String json = Streams.streamToString(inputStream);
			connection.disconnect();

			JSONParser parser = new JSONParser();
			try {
				JSONObject obj = (JSONObject) parser.parse(json);
				sessionId = (String) obj.get("Data");
			} catch (ParseException e) {
				ThisApp.showToast("Parse exception");
				throw new IOException();
			}
		}
		
		boolean isModer;
		isModerObtaining: {
			HttpURLConnection connection = (HttpURLConnection) new URL(
				"http://api.ecampus.kpi.ua/BulletinBoard/DeskIsModerator?" +
				"sessionId=" + sessionId
			).openConnection();
			//connection.setRequestProperty("Accept-Charset", "UTF-8");
			if (connection.getResponseCode() == 403) throw new AuthException();
			if (connection.getResponseCode() == 500) throw new IOException();
			InputStream inputStream = connection.getInputStream();
			String json = Streams.streamToString(inputStream);
			connection.disconnect();
			
			JSONParser parser = new JSONParser();
			try {
				JSONObject obj = (JSONObject) parser.parse(json);
				isModer = (boolean) obj.get("Data");
			} catch (ParseException e) {
				ThisApp.showToast("Parse exception");
				throw new IOException();
			}
		}
		
		user = new User(
			login, 
			sessionId,
			isModer
		);
		
	}

	public static class AccessException extends Exception {}

	public static ArrayList<Post> getPosts() throws IOException, AccessException {
		
		HttpURLConnection connection = (HttpURLConnection) new URL(
			"http://api.ecampus.kpi.ua/BulletinBoard/DeskGetActualBulletins?" +
			"sessionId=" + user.sessionId
		).openConnection();
		//connection.setRequestProperty("Accept-Charset", "UTF-8");
		if (connection.getResponseCode() == 403) throw new AccessException();
		InputStream inputStream = connection.getInputStream();
		String json = Streams.streamToString(inputStream);
		connection.disconnect();
		
		ArrayList<Post> posts = new ArrayList<Post>();

		JSONParser parser = new JSONParser();
		try {
			JSONArray arr = (JSONArray) ((JSONObject) parser.parse(json)).get("Data");
			for (JSONObject obj : arr) {
				posts.add(new Post(
					(int)(long)obj.get("BulletinId"),
					(String)obj.get("Subject"),
					(String)obj.get("Text"),
					(String)obj.get("CreatorName"),
					DateParser.parseDate((String)obj.get("CreationDate")),
					DateParser.parseDate((String)obj.get("ModifiedDate")),
					(boolean)obj.get("Editable")
				));
			}
		} catch (ParseException e) {
			ThisApp.showToast("Parse exception");
			throw new IOException();
		}
		
		return posts;
	}

	public static void postPost(Post post) throws IOException, AccessException {
		HttpURLConnection connection = (HttpURLConnection) new URL(
			"http://api.ecampus.kpi.ua/BulletinBoard/DeskAddBulletein"
		).openConnection();
		//connection.setRequestProperty("Accept-Charset", "UTF-8");
		OutputStream stream = connection.getOutputStream();
		
		JSONObject obj = new JSONObject();
		obj.put("BulletinId", post.id);
		obj.put("Subject", post.subject);
		obj.put("Text", post.body);
		obj.put("CreatorName", user.name);
		//obj.put("CreatorId", user.id);
		try {obj.put("GroupId", post.group.id);} 
			catch (NullPointerException e) {obj.put("GroupId", -1);}
		try {obj.put("ProfileId", post.profile.id);} 
			catch (NullPointerException e) {obj.put("ProfileId", -1);}
		try {obj.put("SubdivisionId", post.subdiv.id);} 
			catch (NullPointerException e) {obj.put("SubdivisionId", -1);}
			
		Streams.stringToStream(stream, obj.toJSONString());
		
		if (connection.getResponseCode() == 501) throw new AccessException();
		connection.disconnect();
	}

	public static void deletePost(Post post) throws IOException, AccessException {
		HttpURLConnection connection = (HttpURLConnection) new URL(
			"http://api.ecampus.kpi.ua/BulletinBoard/Delete?" +
			"sessionId=" + user.sessionId + "&" +
			"bulletinBoardId=" + post.id
		).openConnection();
		//connection.setRequestProperty("Accept-Charset", "UTF-8");
		if (connection.getResponseCode() == 501) throw new AccessException();
		connection.disconnect();
	}

	public static ArrayList<Subdiv> getSubdivs() throws IOException, AccessException {
		
		HttpURLConnection connection = (HttpURLConnection) new URL(
			"http://api.ecampus.kpi.ua/BulletinBoard/DeskGetFacultyTypesList"
		).openConnection();
		//connection.setRequestProperty("Accept-Charset", "UTF-8");
		if (connection.getResponseCode() == 403) throw new AccessException();
		InputStream inputStream = connection.getInputStream();
		String json = Streams.streamToString(inputStream);
		connection.disconnect();

		ArrayList<Subdiv> subdivs = new ArrayList<Subdiv>();

		JSONParser parser = new JSONParser();
		try {
			JSONArray arr = (JSONArray) ((JSONObject) parser.parse(json)).get("Data");
			for (JSONObject obj : arr) {
				subdivs.add(new Subdiv(
					(int)(long)obj.get("Id"),
					(String)obj.get("Name")
				));
			}
		} catch (ParseException e) {
			ThisApp.showToast("Parse exception");
		}

		return subdivs;
		
	}

	public static ArrayList<Group> getGroups(Subdiv subdiv) throws IOException, AccessException {
		
		HttpURLConnection connection = (HttpURLConnection) new URL(
			"http://api.ecampus.kpi.ua/BulletinBoard/DeskGetGroupTypesList?" +
			"subdivisionId=" + subdiv.id
		).openConnection();
		//connection.setRequestProperty("Accept-Charset", "UTF-8");
		if (connection.getResponseCode() == 403) throw new AccessException();
		InputStream inputStream = connection.getInputStream();
		String json = Streams.streamToString(inputStream);
		connection.disconnect();

		ArrayList<Group> groups = new ArrayList<Group>();

		JSONParser parser = new JSONParser();
		try {
			JSONArray arr = (JSONArray) ((JSONObject) parser.parse(json)).get("Data");
			for (JSONObject obj : arr) {
				groups.add(new Group(
					(int)(long)obj.get("Id"),
					(String)obj.get("Name")
				));
			}
		} catch (ParseException e) {
			ThisApp.showToast("Parse exception");
		}

		return groups;
		
	}

	public static ArrayList<Profile> getProfiles() throws IOException, AccessException {
		
		HttpURLConnection connection = (HttpURLConnection) new URL(
			"http://api.ecampus.kpi.ua/BulletinBoard/DeskGetProfileTypesList"
		).openConnection();
		//connection.setRequestProperty("Accept-Charset", "UTF-8");
		if (connection.getResponseCode() == 403) throw new AccessException();
		InputStream inputStream = connection.getInputStream();
		String json = Streams.streamToString(inputStream);
		connection.disconnect();

		ArrayList<Profile> profiles = new ArrayList<Profile>();

		JSONParser parser = new JSONParser();
		try {
			JSONArray arr = (JSONArray) ((JSONObject) parser.parse(json)).get("Data");
			for (JSONObject obj : arr) {
				profiles.add(new Profile(
					(int)(long)obj.get("Id"),
					(String)obj.get("Name")
				));
			}
		} catch (ParseException e) {
			ThisApp.showToast("Parse exception");
		}

		return profiles;
		
	}
	
	
}
