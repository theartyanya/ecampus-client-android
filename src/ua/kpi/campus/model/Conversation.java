package ua.kpi.campus.model;

import ua.kpi.campus.api.jsonparsers.message.UserConversationData;
import ua.kpi.campus.utils.Time;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/21/13
 */
public final class Conversation {
    private final int groupId;
    private final String subject;
    private final String lastMessageText;
    private final long lastMessageDate;

    public Conversation(int groupId, String subject, String lastMessageText, long lastMessageDate) {
        this.groupId = groupId;
        this.subject = subject;
        this.lastMessageText = lastMessageText;
        this.lastMessageDate = lastMessageDate;
    }

    public Conversation(UserConversationData data) {
        groupId = data.getGroupId();
        subject = data.getSubject();
        lastMessageText = data.getLastMessageText();
        lastMessageDate = Time.getUnixTime(data.getLastMessageDate());
    }

    public long getLastMessageDate() {
        return lastMessageDate;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getSubject() {
        return subject;
    }
}
