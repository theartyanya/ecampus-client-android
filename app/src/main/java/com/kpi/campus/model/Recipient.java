package com.kpi.campus.model;

/**
 * Represent Recipient of bulletin entity.
 * <p>
 * Created by Administrator on 25.02.2016.
 */
public class Recipient {

    private String mName;

    private String subdivisionId;

    private String profileId;

    private String studyGroupId;

    public Recipient() {
    }

    public Recipient(String subdivId, String profileId, String groupId) {
        this.subdivisionId = subdivId;
        this.profileId = profileId;
        this.studyGroupId = groupId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public String getSubdivisionId() {
        return subdivisionId;
    }

    public void setSubdivisionId(String subdivisionId) {
        this.subdivisionId = subdivisionId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getStudyGroupId() {
        return studyGroupId;
    }

    public void setStudyGroupId(String studyGroupId) {
        this.studyGroupId = studyGroupId;
    }
}
