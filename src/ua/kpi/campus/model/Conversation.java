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
    private String lastMessageText;

    public Conversation(int groupId, String subject, String lastMessageText) {
        this.groupId = groupId;
        this.subject = subject;
        this.lastMessageText = lastMessageText;
    }

    public Conversation() {
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
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
