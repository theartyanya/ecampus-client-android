package ua.kpi.campus.model;

import ua.kpi.campus.api.jsonparsers.message.MessageItem;
import ua.kpi.campus.utils.Time;

/**
 * DB representation of message
 *
 * @author Artur Dzidzoiev
 * @version 12/21/13
 */
public final class Message {
    private final int messageId;
    private final int groupId;
    private final long timeSent;
    private final String text;
    private final String subject;
    private final int senderId;

    public Message(int messageId, int groupId, long timeSent, String text, String subject, int senderId) {
        this.messageId = messageId;
        this.groupId = groupId;
        this.timeSent = timeSent;
        this.text = text;
        this.subject = subject;
        this.senderId = senderId;
    }

    public Message(MessageItem messageItem) {
        this.messageId = messageItem.getMessageID();
        this.groupId = messageItem.getMessageGroupID();
        this.timeSent = Time.getUnixTimeMessage(messageItem.getDateSent());
        this.text = messageItem.getText();
        this.subject = messageItem.getSubject();
        this.senderId = messageItem.getSenderUserAccountID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (messageId != message.messageId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return messageId;
    }

    public String getSubject() {
        return subject;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getGroupId() {
        return groupId;
    }

    public long getTimeSent() {
        return timeSent;
    }

    public String getText() {
        return text;
    }

    public int getSenderId() {
        return senderId;
    }
}
