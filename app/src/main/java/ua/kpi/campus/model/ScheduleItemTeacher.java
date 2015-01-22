package ua.kpi.campus.model;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitry on 20.01.15.
 */
public class ScheduleItemTeacher extends ScheduleItem {
    private String groupsJSON;

    public void setGroupsJSON(String groupsJSON){
        this.groupsJSON=groupsJSON;
    }
    public String getGroupsJSON(){
        return groupsJSON;
    }


    public ArrayList<Pair<Integer,String>> getGroups(String groupsJSON){
        ArrayList<Pair<Integer,String>> returnList = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(groupsJSON);
            for(int j = 0; j<groupsJSON.length();j++){
                JSONObject group = array.getJSONObject(j);
                returnList.add(new Pair<Integer,String>(group.getInt("group_id"),group.getString("group_full_name")));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return returnList;

    }
    public String getGroupNamesString(String groupsJSON){
        String returnString="";
        try{
            JSONArray array = new JSONArray(groupsJSON);
            for(int j = 0; j<array.length();j++){
                JSONObject group = array.getJSONObject(j);
                returnString+=group.getString("group_full_name")+", ";
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return returnString.toUpperCase();
    }
    @Override
    public String toString(){

        return "toString isn't implemented";
    }
}
