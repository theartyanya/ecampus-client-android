package ua.kpi.campus;

import ua.kpi.campus.jsonparsers.Authorization;
import ua.kpi.campus.jsonparsers.JSONAuthorizationParser;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 25  2013
 */
public class APIInterpreter {
	public Authorization getAuthorization(String login, String password) {
		return JSONAuthorizationParser.parse(CampusApi.auth(login, password));
	}
}
