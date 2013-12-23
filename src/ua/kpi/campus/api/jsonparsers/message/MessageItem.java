package ua.kpi.campus.api.jsonparsers.message;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 17.12.13
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
public class MessageItem {
    private  final int senderUserAccountID;
    private  final int messageID;
    private  final int messageGroupID;
    private  final MessageDetail messageDetail;
    private final String dateSent;
    private final String subject;
    private final String text;

    public MessageItem(int senderUserAccountID, int messageID, int messageGroupID, MessageDetail messageDetail, String dateSent, String subject, String text) {
        this.senderUserAccountID = senderUserAccountID;
        this.messageID = messageID;
        this.messageGroupID = messageGroupID;
        this.messageDetail = messageDetail;
        this.dateSent = dateSent;
        this.subject = subject;
        this.text = text;
    }

    public int getSenderUserAccountID() {
        return senderUserAccountID;
    }

    public int getMessageID() {
        return messageID;
    }

    public int getMessageGroupID() {
        return messageGroupID;
    }

    public MessageDetail getMessageDetail() {
        return messageDetail;
    }

    public String getDateSent() {
        return dateSent;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageItem that = (MessageItem) o;

        if (messageID != that.messageID) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return messageID;
    }
}
