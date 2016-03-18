package ua.kpi.campus.activity.messenger;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Initial single XMPPTCPConnection
 * Created by Serhiy on 16.07.2014.
 */
public class JabberConnection {
    private static XMPPTCPConnection connection = null;
    private static ConnectionConfiguration config = null;

    /**
     * Refresh ConnectionConfiguration
     */
    public static synchronized void reconfigurate(){
        config = new ConnectionConfiguration(JabberAuth.ipAddress, JabberAuth.port,
                JabberAuth.serverName);
    }


    public static synchronized XMPPTCPConnection getConnection(){
        if(connection == null){
            reconfigurate();
            connection = new XMPPTCPConnection(config);
        }
        return connection;
    }
}
