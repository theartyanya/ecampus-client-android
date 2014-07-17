package ua.kpi.campus.Activity.messenger;

/**
 * Created by Serhiy on 15.07.2014.
 */
public class JabberAuth {
    public static String login = "123";
    public static String password = "123";
    public static String serverName = "jabber.ecampus.kpi.ua";
    public static String ipAddress = "192.168.1.126";
    public static int port = 5222;

    public static void setLoginAndPass(String log, String pass){
        login = log;
        password = pass;
    }
}
