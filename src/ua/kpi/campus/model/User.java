package ua.kpi.campus.model;

import ua.kpi.campus.api.jsonparsers.message.UserMessage;

/**
 * DB representation of user.
 *
 * @author Artur Dzidzoiev
 * @version 12/21/13
 */
public final class User {
    private final int id;
    private final String fullname;
    private final String photo;

    public User(int id, String fullname, String photo) {
        this.id = id;
        this.fullname = fullname;
        this.photo = photo;
    }

    public User(UserMessage user) {
        id = user.getUserAccountID();
        fullname = user.getFullName();
        photo = user.getPhoto();
    }

    public String getPhoto() {
        return photo;
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
