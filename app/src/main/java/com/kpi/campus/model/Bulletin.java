package com.kpi.campus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents Bulletin entity
 *
 * Created by Administrator on 02.02.2016.
 */
public class Bulletin {

    @SerializedName("id")
    private String mId;

    @SerializedName("Subject")
    private String mSubject;

    @SerializedName("Text")
    private String mText;

    @SerializedName("CreatorName")
    private String mCreatorName;

    @SerializedName("Profile")
    private String mProfile;

    @SerializedName("Subdivision")
    private String mSubdivision;

    @SerializedName("DateCreate")
    private String mDateCreate;

    @SerializedName("DateStart")
    private String mDateStart;

    @SerializedName("DateEnd")
    private String mDateEnd;

    public Bulletin() {}

    public Bulletin(String date, String theme, String author) {
        mDateStart = date;
        mSubject = theme;
        mCreatorName = author;
    }

    public String getId() {
        return mId;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getCreatorName() {
        return mCreatorName;
    }

    public String getProfile() {
        return mProfile;
    }

    public String getSubdivision() {
        return mSubdivision;
    }

    public String getDateCreate() {
        return mDateCreate;
    }

    public String getDateStart() {
        return mDateStart;
    }

    public String getDateEnd() {
        return mDateEnd;
    }
}
