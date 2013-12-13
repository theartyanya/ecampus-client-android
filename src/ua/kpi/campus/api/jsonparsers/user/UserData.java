package ua.kpi.campus.api.jsonparsers.user;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 09.12.13
 * Time: 0:42
 * To change this template use File | Settings | File Templates.
 */
public  class UserData {
    private final int userAccountID;
    private final String  photo;
    private final String fullName;
    private final Object scientificInterest;
    private final ArrayList<Profile> profiles;
    private  boolean isEmployee=false;

    public UserData(int userAccountID, String photo, String fullName, Object scientificInterest, ArrayList<Profile> profiles) {
        this.userAccountID = userAccountID;
        this.photo = photo;
        this.fullName = fullName;
        this.scientificInterest = scientificInterest;

        this.profiles = profiles;
    }

    public String getPhoto() {
        return photo;
    }

    public String getFullName() {
        return fullName;
    }

    public Object getScientificInterest() {
        return scientificInterest;
    }

    public boolean isEmployee() {
        return isEmployee;
    }
    public void setIsEmployee(boolean isEmployee){
        this.isEmployee=isEmployee;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public int getUserAccountID() {
        return userAccountID;
    }
}
