package ua.kpi.campus.model;

/**
 * DB representation of user.
 *
 * @author Artur Dzidzoiev
 * @version 12/21/13
 */
public class User {
    private int id;
    private String fullname;
    private String photo;

    public User() {
    }

    public User(int id, String fullname, String photo) {
        this.id = id;
        this.fullname = fullname;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


}
