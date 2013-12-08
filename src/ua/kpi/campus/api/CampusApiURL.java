package ua.kpi.campus.api;

/**
 * Main Campus API
 * 
 * @author Artur Dzidzoiev
 * @version Nov 24, 2013
 * @see <a href="http://sdrv.ms/1gUsc17"/>
 * @see <a href="http://dev.ecampus.kpi.ua/library/item/campus-api-for-mobile"/>
 */
public class CampusApiURL {
	/**
	 * API Campus link
	 */
	private final static String API_URL = "http://api.ecampus.kpi.ua/";
	private final static String AUTH_PATH = "User/Auth?";
	private final static String GET_PERMISSION_PATH = "User/GetPermissions?";

    /**
	 * Logging in to Campus system
	 * 
	 * @return JSON - string
	 */
	public static String getAuth(String login, String password) {
		return String.format("%s%slogin=%s&password=%s",API_URL, AUTH_PATH, login, password);
	}

	/**
	 * Getting permissions to Campus system
	 * 
	 * @return JSON - string
	 */
	public static String getPermission(String data){
		return String.format("%s%ssessionId=%s",API_URL, GET_PERMISSION_PATH, data);
	}

}
