package ua.kpi.campus.model;

import ua.kpi.campus.api.jsonparsers.message.UserConversationData;

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

    public Conversation(int groupId, String subject, String lastMessageText) {
        this.groupId = groupId;
        this.subject = subject;
        this.lastMessageText = lastMessageText;
    }

    public Conversation(UserConversationData data) {
        groupId = data.getGroupId();
        subject = data.getSubject();
        lastMessageText = data.getLastMessageText();
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
