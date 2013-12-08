package ua.kpi.campus.api.jsonparsers;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 09.12.13
 * Time: 0:42
 * To change this template use File | Settings | File Templates.
 */
public class SessionIDData {
    private final int userAccountID;
    private final String  photo;
    private final String fullName;
    private final Object scientificInterest;
    private final ArrayList<Object> personalities;
    private final ArrayList<Employee> employees;
    private final ArrayList<Profile> profiles;

    public SessionIDData(int userAccountID, String photo, String fullName, Object scientificInterest, ArrayList<Object> personalities, ArrayList<Employee> employees, ArrayList<Profile> profiles) {
        this.userAccountID = userAccountID;
        this.photo = photo;
        this.fullName = fullName;
        this.scientificInterest = scientificInterest;
        this.personalities = personalities;
        this.employees = employees;
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

    public ArrayList<Object> getPersonalities() {
        return personalities;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public int getUserAccountID() {
        return userAccountID;
    }
}
