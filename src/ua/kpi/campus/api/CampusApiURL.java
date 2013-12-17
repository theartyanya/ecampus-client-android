package ua.kpi.campus.api;

/**
 * Getting URL for main Campus APIs
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
    private final static String GET_USER_DATA_PATH =  "User/Get?";
    private final static String GET_CURRENT_USER_DATA_PATH =  "User/GetCurrentUser?";
    private final static String GET_CONVERSATIONS_PATH = "message/GetUserConversations?";
    private final static String GET_CONVERSATION_PATH = "message/GetUserConversation?";


    /**
	 * Logging in to Campus system
	 * 
	 * @return URL
	 */
	public static String getAuth(String login, String password) {
		return String.format("%s%slogin=%s&password=%s",API_URL, AUTH_PATH, login, password);
	}

	/**
	 * Getting permissions to Campus system
	 * 
	 * @return URL
	 */
	public static String getPermission(String data) {
		return String.format("%s%ssessionId=%s",API_URL, GET_PERMISSION_PATH, data);
	}

    /**
     * Getting current user
     *
     * @return URL
     */
    public static String getCurrentUser(String sessionId) {
        return String.format("%s%ssessionId=%s",API_URL, GET_CURRENT_USER_DATA_PATH, sessionId);
    }

    /**
     * Getting user data
     *
     * @return URL
     */
    public static String getUserData(String sessionId, String userId) {
        return String.format("%s%ssessionId=%s&userId=%s", API_URL, GET_USER_DATA_PATH, sessionId, userId);
    }

    /**
     * Getting message list
     *
     * @return URL
     */
    public static String getConversations(String sessionId) {
        return String.format("%s%ssessionId=%s", API_URL, GET_CONVERSATIONS_PATH, sessionId);
    }

    /**
     * Getting current conversation
     *
     * @return URL
     */
    public static String getConversation(String sessionId, String groupId, int page, int size) {
        return String.format("%s%ssessionId=%s&groupId=%s&page=%d&size=%d",
                API_URL, GET_CONVERSATION_PATH,
                sessionId, groupId, page, size);
    }

    /**
     * CreateGroup
     *
     * @return URL
     * @deprecated
     */
    //TODO need rewriting
    public static String getConversationCreateGroupPath(String sessionId, String groupId, int page, int size) {
        return String.format("%s%ssessionId=%s&groupId=%s&page=%d&size=%d",
                API_URL, GET_CONVERSATION_PATH,
                sessionId, groupId, page, size);
    }


}
