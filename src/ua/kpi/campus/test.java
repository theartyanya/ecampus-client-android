package ua.kpi.campus;

import org.json.JSONException;
import ua.kpi.campus.api.jsonparsers.JSONConversationParser;
import ua.kpi.campus.api.jsonparsers.JsonObject;
import ua.kpi.campus.api.jsonparsers.message.UserConversationData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 16.12.13
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
public class test {
    public static void main(String[] args){
        try{
        JsonObject<ArrayList<UserConversationData>> u= JSONConversationParser.parse(Mock.getUSER_CONVERSATION());
        }catch (JSONException e){}
    }
}
