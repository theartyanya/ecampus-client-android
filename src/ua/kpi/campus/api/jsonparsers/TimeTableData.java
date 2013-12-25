package ua.kpi.campus.api.jsonparsers;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 25.12.13
 * Time: 0:57
 * To change this template use File | Settings | File Templates.
 */
public class TimeTableData {
   private final String employee;
    private final String personality;
    private final String subject;
    private final String building;
    private final String employeePhotoPath;
    private final int weekNum;
    private final int dayId;
    private final String dayName;
    private final int lessonId;

    public TimeTableData(String employee, String personality, String subject,String building, String employeePhotoPath, int weekNum, int dayId, String dayName, int lessonId) {
        this.employee = employee;
        this.personality = personality;
        this.subject = subject;
        this.employeePhotoPath = employeePhotoPath;
        this.weekNum = weekNum;
        this.dayId = dayId;
        this.dayName = dayName;
        this.lessonId = lessonId;
        this.building=building;
    }

    public String getBuilding() {
        return building;
    }

    public String getEmployee() {
        return employee;
    }

    public String getPersonality() {
        return personality;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmployeePhotoPath() {
        return employeePhotoPath;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public int getDayId() {
        return dayId;
    }

    public String getDayName() {
        return dayName;
    }

    public int getLessonId() {
        return lessonId;
    }
}
