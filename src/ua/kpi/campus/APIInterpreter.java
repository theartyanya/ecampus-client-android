package ua.kpi.campus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import ua.kpi.campus.jsonparsers.Authorization;
import ua.kpi.campus.jsonparsers.JSONAuthorizationParser;

import android.util.Log;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 25 лист. 2013
 */
public class APIInterpreter {
	public Authorization getAuthorization(String login, String password) {
		return JSONAuthorizationParser.parse(CampusApi.auth(login, password));
	}
}
