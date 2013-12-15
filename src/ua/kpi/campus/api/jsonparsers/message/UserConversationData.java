package ua.kpi.campus.api.jsonparsers.message;

import ua.kpi.campus.api.jsonparsers.message.User;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 15.12.13
 * Time: 18:13
 * To change this template use File | Settings | File Templates.
 */
public class UserConversationData {
    final String subject;
   final ArrayList<User> users;
    final String lastMessageText;
    final String lastMessageDate;
    final int groupId;

    public UserConversationData(String subject, ArrayList<User> users, String lastMessageText, String lastMessageDate, int groupId) {
        this.subject = subject;
        this.users = users;
        this.lastMessageText = lastMessageText;
        this.lastMessageDate = lastMessageDate;
        this.groupId = groupId;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
