package ua.kpi.campus.api.jsonparsers.message;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 17.12.13
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
public class MessageItem {
    private  final int sendlerUserAccountID;
    private  final int messageID;
    private  final int messageGroupID;
    private  final MessageDetail messageDetail;
    private final String dateSent;
    private final String subject;
    private final String text;

    public MessageItem(int sendlerUserAccountID, int messageID, int messageGroupID, MessageDetail messageDetail, String dateSent, String subject, String text) {
        this.sendlerUserAccountID = sendlerUserAccountID;
        this.messageID = messageID;
        this.messageGroupID = messageGroupID;
        this.messageDetail = messageDetail;
        this.dateSent = dateSent;
        this.subject = subject;
        this.text = text;
    }

    public int getSendlerUserAccountID() {
        return sendlerUserAccountID;
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
}
