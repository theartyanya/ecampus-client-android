package com.kpi.campus.model.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents Bulletin entity
 *
 * Created by Administrator on 02.02.2016.
 */
public class Bulletin {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("creatorName")
    @Expose
    private String creatorName;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("subdivision")
    @Expose
    private String subdivision;
    @SerializedName("dateCreate")
    @Expose
    private String dateCreate;
    @SerializedName("dateStart")
    @Expose
    private String dateStart;
    @SerializedName("dateEnd")
    @Expose
    private String dateEnd;

    public Bulletin(String id, String theme, String author, String dateCreate) {
        this.id = id;
        this.subject = theme;
        this.creatorName = author;
        this.dateCreate = dateCreate;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject
     * The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     *
     * @param creatorName
     * The creatorName
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     *
     * @return
     * The profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     *
     * @param profile
     * The profile
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     *
     * @return
     * The subdivision
     */
    public String getSubdivision() {
        return subdivision;
    }

    /**
     *
     * @param subdivision
     * The subdivision
     */
    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    /**
     *
     * @return
     * The dateCreate
     */
    public String getDateCreate() {
        return dateCreate;
    }

    /**
     *
     * @param dateCreate
     * The dateCreate
     */
    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    /**
     *
     * @return
     * The dateStart
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     *
     * @param dateStart
     * The dateStart
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    /**
     *
     * @return
     * The dateEnd
     */
    public String getDateEnd() {
        return dateEnd;
    }

    /**
     *
     * @param dateEnd
     * The dateEnd
     */
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
