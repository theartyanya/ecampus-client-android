package ua.kpi.campus.api.jsonparsers.message;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 17.12.13
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */
public class MessageDetail {
    private  final int MessageDetailsID;
    private  final String dataView;

   public MessageDetail(int messageDetailsID, String dataView) {
        MessageDetailsID = messageDetailsID;
        this.dataView = dataView;
    }

    int getMessageDetailsID() {
        return MessageDetailsID;
    }

    String getDataSent() {
        return dataView;
    }
}