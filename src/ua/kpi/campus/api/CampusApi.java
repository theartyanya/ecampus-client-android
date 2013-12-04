package ua.kpi.campus.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Main Campus API
 * 
 * @author Artur Dzidzoiev
 * @version Nov 24, 2013
 * @see <a href="http://sdrv.ms/1gUsc17"/>
 * @see <a href="http://dev.ecampus.kpi.ua/library/item/campus-api-for-mobile"/>
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
		String parameters = String.format("%slogin=%s&password=%s", AUTH_PATH, login, password);
		return getStingFromHTTP(parameters);
	}

	/**
	 * Getting permissions to Campus system
	 * 
	 * @return JSON - string
	 */
	public static String getPermission(String data){
		String parameters = String.format("%ssessionId=%s", GET_PERMISSION_PATH, data);
		return getStingFromHTTP(parameters);
	}

	private static String getStingFromHTTP(String parameters) {
        String str="error";
		try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(API_URL+parameters);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                str = EntityUtils.toString(resEntity);
            }
        } catch (UnsupportedEncodingException uee){
            uee.printStackTrace();
        } catch (ClientProtocolException cpe){
            cpe.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        return str;
    }
}
