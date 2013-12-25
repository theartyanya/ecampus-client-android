package ua.kpi.campus.Activity.userlist;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public final class UserModel {
    private final int id;
    private final String name;
    private final String photo;
    private boolean selected;

    public UserModel(int id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        selected = false;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }
}
