package ua.kpi.campus.ui.view;

/**
 * Created by Worck on 11.07.2016.
 */
public class DrawerItem {
    int ItemName;
    int imgResID;

    public DrawerItem(int itemName, int imgResID) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
    }

    public int getItemName() {
        return ItemName;
    }

    public void setItemName(int itemName) {
        ItemName = itemName;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
}
