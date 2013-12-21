package ua.kpi.campus.model;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/21/13
 */
public class Conversation {
    private int groupId;
    private String subject;
    private int lastMessageId;

    public Conversation() {
    }

    public int getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(int lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
