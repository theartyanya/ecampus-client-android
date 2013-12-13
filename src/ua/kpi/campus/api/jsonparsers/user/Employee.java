package ua.kpi.campus.api.jsonparsers.user;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 09.12.13
 * Time: 0:41
 * To change this template use File | Settings | File Templates.
 */
public class Employee {
    private final int subDivisionID;
    private final String subDivisionName;
    private final String position;
    private final String academicDegree;
    private final String academicStatus;

    public Employee(int subDivisionID, String subDivisionName, String position, String academicDegree, String academicStatus) {
        this.subDivisionID = subDivisionID;
        this.subDivisionName = subDivisionName;
        this.position = position;
        this.academicDegree = academicDegree;
        this.academicStatus = academicStatus;
    }

    public String getSubDivisionName() {
        return subDivisionName;
    }

    public String getPosition() {
        return position;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public int getSubDivisionID() {
        return subDivisionID;
    }
}
