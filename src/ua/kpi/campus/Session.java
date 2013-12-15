package ua.kpi.campus;

import ua.kpi.campus.api.jsonparsers.user.UserData;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/15/13
 */
public class Session {
    private static UserData currentUser;
    private static String sessionId;

    public static UserData getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserData currentUser) {
        Session.currentUser = currentUser;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        Session.sessionId = sessionId;
    }
}
