package com.kpi.campus.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents Bulletin entity
 * <p>
 * Created by Administrator on 02.02.2016.
 */
public class Bulletin implements Parcelable {

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
    @SerializedName("actuality")
    @Expose
    private boolean actuality;

    public Bulletin(String id, String theme, String author, String dateCreate) {
        this.id = id;
        this.subject = theme;
        this.creatorName = author;
        this.dateCreate = dateCreate;
    }

    private Bulletin(Parcel in) {
        id = in.readString();
        subject = in.readString();
        text = in.readString();
        creatorName = in.readString();
        subdivision = in.readString();
        dateCreate = in.readString();
        dateStart = in.readString();
        dateEnd = in.readString();
        actuality = in.readByte() != 0;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName The creatorName
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return The profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @param profile The profile
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * @return The subdivision
     */
    public String getSubdivision() {
        return subdivision;
    }

    /**
     * @param subdivision The subdivision
     */
    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    /**
     * @return The dateCreate
     */
    public String getDateCreate() {
        return dateCreate;
    }

    /**
     * @param dateCreate The dateCreate
     */
    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    /**
     * @return The dateStart
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     * @param dateStart The dateStart
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * @return The dateEnd
     */
    public String getDateEnd() {
        return dateEnd;
    }

    /**
     * @param dateEnd The dateEnd
     */
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * @return The actuality
     */
    public boolean getActuality() {
        return actuality;
    }

    /**
     * @param actuality The actuality
     */
    public void setActuality(boolean actuality) {
        this.actuality = actuality;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation.
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(subject);
        dest.writeString(text);
        dest.writeString(creatorName);
        dest.writeString(subdivision);
        dest.writeString(dateCreate);
        dest.writeString(dateStart);
        dest.writeString(dateEnd);
        dest.writeByte((byte) (actuality ? 1 : 0));
    }

    /**
     * Must have a non-null static field called CREATOR of a type that implements the Parcelable.Creator interface
     */
    public static final Parcelable.Creator<Bulletin> CREATOR
            = new Parcelable.Creator<Bulletin>() {
        public Bulletin createFromParcel(Parcel in) {
            return new Bulletin(in);
        }

        public Bulletin[] newArray(int size) {
            return new Bulletin[size];
        }
    };

}
