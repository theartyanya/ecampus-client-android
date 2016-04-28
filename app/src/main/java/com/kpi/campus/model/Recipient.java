package com.kpi.campus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represent Recipient of bulletin entity.
 * <p>
 * Created by Administrator on 25.02.2016.
 */
public class Recipient {

    @SerializedName("subdivisionId")
    @Expose
    private String subdivisionId;

    @SerializedName("subdivisionName")
    @Expose
    private String subdivisionName;

    @SerializedName("profileId")
    @Expose
    private String profileId;

    @SerializedName("profileName")
    @Expose
    private String profileName;

    @SerializedName("studyGroupId")
    @Expose
    private String studyGroupId;

    @SerializedName("studyGroupName")
    @Expose
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
