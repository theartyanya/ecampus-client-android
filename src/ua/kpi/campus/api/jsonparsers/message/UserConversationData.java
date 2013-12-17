package ua.kpi.campus.api.jsonparsers.message;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 15.12.13
 * Time: 18:13
 * To change this template use File | Settings | File Templates.
 */
public final class UserConversationData {
    private final String subject;
    private final ArrayList<User> users;
    private final String lastMessageText;
    private final String lastMessageDate;
    private final int groupId;

    public UserConversationData(String subject, ArrayList<User> users, String lastMessageText, String lastMessageDate, int groupId) {
        this.subject = subject;
        this.users = users;
        this.lastMessageText = lastMessageText;
        this.lastMessageDate = lastMessageDate;
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
