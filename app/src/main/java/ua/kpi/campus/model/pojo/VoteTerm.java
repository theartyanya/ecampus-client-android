package ua.kpi.campus.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 01.06.2016.
 */
public class VoteTerm {

    @SerializedName("voteId")
    @Expose
    private String voteId;
    @SerializedName("voteName")
    @Expose
    private String voteName;
    @SerializedName("dateStart")
    @Expose
    private String dateStart;
    @SerializedName("dateStop")
    @Expose
    private String dateStop;

    public VoteTerm(String id, String name, String dateStart, String dateStop) {
        voteId = id;
        voteName = name;
        this.dateStart = dateStart;
        this.dateStop = dateStop;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateStop() {
        return dateStop;
    }

    public void setDateStop(String dateStop) {
        this.dateStop = dateStop;
    }
}
