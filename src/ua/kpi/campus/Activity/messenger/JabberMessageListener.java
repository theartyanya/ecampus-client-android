package ua.kpi.campus.Activity.messenger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by Serhiy on 18.07.2014.
 */
public class JabberMessageListener implements MessageListener {
    JabberSmackMessageAPI messageAPI;
    public JabberMessageListener(JabberSmackMessageAPI api){
        messageAPI = api;
    }
    @Override
    public void processMessage(Chat chat, Message message) {
        messageAPI.processMessage(chat, message);
    }
}
