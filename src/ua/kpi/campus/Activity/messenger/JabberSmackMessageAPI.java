package ua.kpi.campus.Activity.messenger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Can send and receive messages.
 * Created by Serhiy on 18.07.2014.
 */
public class JabberSmackMessageAPI implements MessageListener {
    /**
     * Include all created and incoming chats
     */
    HashMap<String, Chat> chats;
    XMPPTCPConnection connection;
    private Viewer viewer;
    public JabberSmackMessageAPI(Viewer v){
        viewer = v;
        connection = JabberConnection.getConnection();
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        viewer.displayNewMessage(new MessageElement(message.getThread(), message.getBody(),
                Calendar.getInstance().getTime()));
        chats.put(chat.getThreadID(), chat);
    }

    public void sendMessage(MessageElement messageElement){
        Chat chat = chats.get(messageElement.tid);
        if(chat != null){
            try {
                chat.sendMessage(new Message(messageElement.body));
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToNewUser(MessageElement messageElement){
        Chat c = ChatManager.getInstanceFor(connection).createChat(messageElement.jid, new JabberMessageListener(this));
        messageElement.tid = c.getThreadID();
        sendMessage(messageElement);
    }
}
