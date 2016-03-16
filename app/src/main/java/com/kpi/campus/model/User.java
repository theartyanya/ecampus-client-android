package com.kpi.campus.model;

import java.util.List;

/**
 * Represent the entity of current logged user.
 *
 * Created by Administrator on 16.03.2016.
 */
public class User {

    private static User mInstance;

    private User() {}

    public static User getInstance() {
        if(mInstance == null) {
            mInstance = new User();
        }
        return mInstance;
    }

    public String name;
    public String email;
    public String profile;
    public String subdivision;
    public List<String> descendantSubdivisions;
    public boolean isBulletinBoardModerator = false;

}
