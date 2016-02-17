package com.kpi.campus.model;

/**
 * Represents Bulletin entity
 *
 * Created by Administrator on 02.02.2016.
 */
public class Bulletin {

    private String mDate;
    private String mTheme;
    private String mAuthor;

    public Bulletin(String date, String theme, String author) {
        mDate = date;
        mTheme = theme;
        mAuthor = author;
    }


    public String getDate() {
        return mDate;
    }

    public String getTheme() {
        return mTheme;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
