package com.kpi.campus.model;

/**
 * Created by Administrator on 28.01.2016.
 */
public class Subsystem {

    private String mName;
    private int mIconId;

    public Subsystem(String name, int iconId) {
        mName = name;
        mIconId = iconId;
    }

    public String getName() {
        return mName;
    }

    public int getIconId() {
        return mIconId;
    }
}
