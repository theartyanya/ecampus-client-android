package ua.kpi.campus.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01.06.2016.
 */
public class VoteTeacher {

    @SerializedName("voteId")
    @Expose
    private Integer voteId;
    @SerializedName("teacherId")
    @Expose
    private Integer teacherId;
    @SerializedName("teacherName")
    @Expose
    private String teacherName;
    @SerializedName("isVoted")
    @Expose
    private boolean isVoted;
    @SerializedName("criteria")
    @Expose
    private List<Item> criteria = new ArrayList<>();
    @SerializedName("avgResult")
    @Expose
    private String avgResult;

    public VoteTeacher(Integer voteId, Integer teacherId, String name, boolean isVoted, String result ) {
        this.voteId = voteId;
        this.teacherId = teacherId;
        this.teacherName = name;
        this.isVoted = isVoted;
        this.avgResult = result;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setIsVoted(boolean isVoted) {
        this.isVoted = isVoted;
    }

    public List<Item> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Item> criteria) {
        this.criteria = criteria;
    }

    public String getAvgResult() {
        return avgResult;
    }

    public void setAvgResult(String avgResult) {
        this.avgResult = avgResult;
    }
}
