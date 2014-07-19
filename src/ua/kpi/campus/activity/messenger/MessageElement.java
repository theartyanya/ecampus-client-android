package ua.kpi.campus.activity.messenger;

import java.util.Date;

/**
 * Created by Serhiy on 18.07.2014.
 */
public class MessageElement {
    public String tid;
    public String jid;
    public String body;
    public Date created;
    public boolean readed = false;

    public MessageElement(String id, String text, Date date){
        tid = id;
        body = text;
        created = date;
    }

    public MessageElement(String id, String text, Date date, String jid){
        tid = id;
        body = text;
        created = date;
        this.jid = jid;
    }
}
