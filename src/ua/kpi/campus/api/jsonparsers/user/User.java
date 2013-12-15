package ua.kpi.campus.api.jsonparsers.user;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 15.12.13
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */
public class User {
    private final int userAccountID;
    private final String photo;
    private final String fullName;
    private final Object scientificInterest;
    private final ArrayList<SubsystemData> profiles;
    private boolean isEmployee;
    private final ArrayList<Employee> employees;
    private final ArrayList<Personality> personalities;

    public User(int userAccountID, String photo, String fullName, Object scientificInterest, ArrayList<SubsystemData> profiles, ArrayList<Employee> employees, ArrayList<Personality> personalities) {
        this.userAccountID = userAccountID;
        this.photo = photo;
        this.fullName = fullName;
        this.scientificInterest = scientificInterest;
        this.profiles = profiles;
        this.employees = employees;
        this.personalities = personalities;
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

    public void setIsEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public ArrayList<SubsystemData> getProfiles() {
        return profiles;
    }

    public int getUserAccountID() {
        return userAccountID;
    }
}
