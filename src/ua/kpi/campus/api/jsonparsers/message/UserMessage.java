package ua.kpi.campus.api.jsonparsers.message;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 15.12.13
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */
public final class UserMessage {
    private final int userAccountID;
    private final String photo;
    private final String fullName;
    private final Object scientificInterest;
    private final Object profiles;
    private boolean isEmployee;
    private final Object employees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMessage that = (UserMessage) o;

        if (isEmployee != that.isEmployee) return false;
        if (userAccountID != that.userAccountID) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userAccountID;
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (isEmployee ? 1 : 0);
        return result;
    }

    private final Object personalities;

    public UserMessage(int userAccountID, String photo, String fullName, Object scientificInterest, Object profiles, Object employees, Object personalities) {
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

    public Object getProfiles() {
        return profiles;
    }

    public int getUserAccountID() {
        return userAccountID;
    }
}
