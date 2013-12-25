package ua.kpi.campus.model;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public final class TimetableElement {
    private final int lessonId;
    private final String employee;
    private final String subject;
    private final String building;
    private final String employeePhotoPath;
    private final int weekNum;
    private final int dayId;
    private final String dayName;

    public TimetableElement(int lessonId, String employee,
                            String subject, String building,
                            String employeePhotoPath, int weekNum,
                            int dayId, String dayName) {
        this.lessonId = lessonId;
        this.employee = employee;
        this.subject = subject;
        this.building = building;
        this.employeePhotoPath = employeePhotoPath;
        this.weekNum = weekNum;
        this.dayId = dayId;
        this.dayName = dayName;
    }

    public int getLessonId() {
        return lessonId;
    }

    public String getEmployee() {
        return employee;
    }

    public String getSubject() {
        return subject;
    }

    public String getBuilding() {
        return building;
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
}
