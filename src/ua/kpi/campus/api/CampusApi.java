package ua.kpi.campus.api;

/**
 * Main Campus API
 * 
 * @author Artur Dzidzoiev
 * @version Nov 24, 2013
 * @see <a href="http://sdrv.ms/1gUsc17"/>
 * @see <a href="http://dev.ecampus.kpi.ua/library/item/campus-api-for-mobile"/>
 * @deprecated
 */
public class CampusApi {
	/**
	 * API Campus link
	 */
	private final static String API_URL = "http://api.ecampus.kpi.ua/";
	private final static String AUTH_PATH = "User/Auth?";
	private final static String GET_PERMISSION_PATH = "User/GetPermissions?";
    public final static String ERROR = "error";

    /**
	 * Logging in to Campus system
	 * 
	 * @return JSON - string
	 */
	public static String auth(String login, String password) {
		String url = String.format("%s%slogin=%s&password=%s",API_URL, AUTH_PATH, login, password);
		return HTTP.getSting(url);
	}

	/**
	 * Getting permissions to Campus system
	 * 
	 * @return JSON - string
	 */
	public static String getPermission(String data){
		String url = String.format("%s%ssessionId=%s",API_URL, GET_PERMISSION_PATH, data);
		return HTTP.getSting(url);
	}

}
