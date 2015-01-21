package ua.kpi.campus.model;

/**
 * Created by dmitry on 20.01.15.
 */
public class ScheduleItemTeacher extends ScheduleItem {
    private int GROUP_ID;
    private String GROUP_NAME;

    public int getGroupId(){return GROUP_ID;}
    public void setGroupId(int id){
        GROUP_ID=id;
    }

    public String getGroupName(){
        return GROUP_NAME;
    }
    public void setGroupName(String groupName){
        GROUP_NAME=groupName;
    }

    @Override
    public String toString(){
        return LESSON_NAME +" + "+ GROUP_NAME;
    }
}
