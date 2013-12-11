package ua.kpi.campus.api.jsonparsers;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 10.12.13
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
public final class UserDataEmployee extends UserData {
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    private final ArrayList<Employee> employees;
    public UserDataEmployee(int userAccountID, String photo, String fullName, Object scientificInterest, ArrayList<Employee> employees, ArrayList<Profile> profiles) {
        super(userAccountID, photo, fullName, scientificInterest, profiles);
        this.employees=employees;
    }
}
