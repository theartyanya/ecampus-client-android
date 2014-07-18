package ua.kpi.campus.Activity.messenger;

import android.os.AsyncTask;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
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
            Thread.sleep(30000);
            Chat c = ChatManager.getInstanceFor(connection).createChat("bai", new MessageListener() {
                @Override
                public void processMessage(Chat chat, Message message) {
                    System.out.println("recv mess");
                }
            });
            c.sendMessage("ololo");
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            System.out.println("connection failed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
