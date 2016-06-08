package ua.kpi.campus.model;

/**
 * Represents teacher's Rating entity for voting.
 * Created by Administrator on 08.06.2016.
 */
public class Rating {

    private float ratingStar;
    private String criterion;

    public Rating(float ratingStar, String criterion) {
        this.ratingStar = ratingStar;
        this.criterion = criterion;
    }

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(float ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }
}
