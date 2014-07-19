package ua.kpi.campus.activity.messenger;

import android.os.AsyncTask;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

/**
 * Created by Serhiy on 16.07.2014.
 */
public class StartJabberConnection extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
        XMPPTCPConnection connection = JabberConnection.getConnection();
        try {
            connection.connect();
            System.out.println("connection successful");
            connection.login(JabberAuth.login, JabberAuth.password);
            System.out.println("loginned successful");
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            System.out.println("connection failed");
        }
        return null;
    }
}
