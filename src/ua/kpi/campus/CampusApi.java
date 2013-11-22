package ua.kpi.campus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class CampusApi {
	private final static String API_URL = "http://api.ecampus.kpi.ua/";

	public static String login(String login, String password) {
		String parameters = String.format("User/Auth?login=%s&password=%s",
				login, password);
		URL url;
		HttpURLConnection connection;
		OutputStreamWriter request;
		String response = "";

		try {
			url = new URL(API_URL + parameters);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// set the request method to GET
			connection.setRequestMethod("GET");
			// get the output stream from the connection you created
			request = new OutputStreamWriter(connection.getOutputStream());
			// write your data to the ouputstream
			request.write(parameters);
			request.flush();
			request.close();
			String line = "";
			// create your inputsream
			InputStreamReader isr = new InputStreamReader(
					connection.getInputStream());
			// read in the data from input stream, this can be done a variety of
			// ways
			BufferedReader reader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			// get the string version of the response data
			response = sb.toString();
			// do what you want with the data now

			// always remember to close your input and output streams
			isr.close();
			reader.close();
		} catch (IOException e) {
			Log.e("HTTP GET:", e.toString());
		}
		return response;
	}
}
