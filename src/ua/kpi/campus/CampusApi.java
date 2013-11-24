package ua.kpi.campus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.util.Log;

/**
 * Main Campus API
 * 
 * @author Artur Dzidzoiev
 * @version Nov 24, 2013
 * @see http://sdrv.ms/1gUsc17
 * @see http://dev.ecampus.kpi.ua/library/item/campus-api-for-mobile
 */
public class CampusApi {
	/**
	 * API Campus link
	 */
	private final static String API_URL = "http://api.ecampus.kpi.ua/";
	private final static String authPath = "User/Auth?";
	private final static String getPermissionPath = "User/GetPermissions?";

	
	/**
	 * Logging in to Campus system
	 * 
	 * @return JSON - string
	 */
	public static String auth(String login, String password) {
		String parameters = String.format("%slogin=%s&password=%s", authPath, login, password);
		return getStingFromHTTP(parameters);
	}

	/**
	 * Getting permissions to Campus system
	 * 
	 * @return JSON - string
	 */
	public static String getPermission(String data){
		String parameters = String.format("%ssessionId=%s", getPermissionPath, data);
		return getStingFromHTTP(parameters);
	}
	
	private static String getStingFromHTTP(String parameters) {
	    String response = "";

		try {
			URL url = CreateURL(parameters);
			HttpURLConnection connection = openConnectionGet(url);
			sendRequest(parameters, connection);
			response = getLine(connection);
		} catch (IOException e) {
			Log.e("HTTP GET:", e.toString());
		}

		return response;
    }
	
	private static String getLine(HttpURLConnection connection)
			throws IOException {
		String response;
		String receivedLine = "";
		// create your input stream
		InputStreamReader isr = new InputStreamReader(
				connection.getInputStream());
		// read in the data from input stream, this can be done a variety of
		// ways
		BufferedReader reader = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		while ((receivedLine = reader.readLine()) != null) {
			sb.append(receivedLine + "\n");
		}
		response = sb.toString();
		isr.close();
		reader.close();
		return response;
	}

	private static void sendRequest(String parameters,
			HttpURLConnection connection) throws IOException {
		OutputStreamWriter request;
		// get the output stream from the connection you created
		request = new OutputStreamWriter(connection.getOutputStream());
		// write your data to the output stream
		request.write(parameters);
		request.flush();
		request.close();
	}

	private static HttpURLConnection openConnectionGet(URL url)
			throws IOException, ProtocolException {
		HttpURLConnection connection;
		connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// set the request method to GET
		connection.setRequestMethod("GET");
		return connection;
	}

	private static URL CreateURL(String parameters)
			throws MalformedURLException {
		return new URL(API_URL + parameters);
	}
}
