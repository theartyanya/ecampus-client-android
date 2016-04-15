package com.kpi.campus.model;

/**
 * Represent Recipient of bulletin entity.
 * <p>
 * Created by Administrator on 25.02.2016.
 */
public class Recipient {

    private String subdivisionId;

    private String subdivisionName;

    private String profileId;

    private String profileName;

    private String studyGroupId;

    private String studyGroupName;

    public Recipient() {
    }

    public Recipient(String subdivId, String profileId, String groupId) {
        this.subdivisionId = subdivId;
        this.profileId = profileId;
        this.studyGroupId = groupId;
    }

    public Recipient(String subdivId, String subdivName, String profileId,
                     String profileName, String groupId, String groupName) {
        this.subdivisionId = subdivId;
        this.subdivisionName = subdivName;
        this.profileId = profileId;
        this.profileName = profileName;
        this.studyGroupId = groupId;
        this.studyGroupName = groupName;
    }

    public String getSubdivisionName() {
        return subdivisionName;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getStudyGroupName() {
        return studyGroupName;
    }
}
