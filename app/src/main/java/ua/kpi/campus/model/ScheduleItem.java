package ua.kpi.campus.model;

import java.io.Serializable;

/**
 * Created by doroshartyom on 08.01.2015.
 */
public class ScheduleItem implements Serializable {

    private int LESSON_ID;
    private int DAY_NUMBER;
    private int LESSON_NUMBER;
    private String LESSON_NAME;
    private String LESSON_ROOM;
    private String TEACHER_NAME;
    private int LESSON_WEEK;
    private String TIME_START;
    private String TIME_END;
    private boolean DEVIDER;


    public int getLessonId() {
        return LESSON_ID;
    }

    public ScheduleItem setLessonId(int LESSON_ID) {
        this.LESSON_ID = LESSON_ID;
        return this;
    }

    public int getDayNumber() {
        return DAY_NUMBER;
    }

    public ScheduleItem setDayNumber(int DAY_NUMBER) {
        this.DAY_NUMBER = DAY_NUMBER;
        return this;
    }

    public int getLessonNumber() {
        return LESSON_NUMBER;
    }

    public ScheduleItem setLessonNumber(int LESSON_NUMBER) {
        this.LESSON_NUMBER = LESSON_NUMBER;
        return this;
    }

    public String getLessonName() {
        return LESSON_NAME;
    }

    public ScheduleItem setLessonName(String LESSON_NAME) {
        this.LESSON_NAME = LESSON_NAME;
        return this;
    }

    public String getLessonRoom() {
        return LESSON_ROOM;
    }

    public ScheduleItem setLessonRoom(String LESSON_ROOM) {
        this.LESSON_ROOM = LESSON_ROOM;
        return this;
    }

    public String getTeacherName() {
        return TEACHER_NAME;
    }

    public ScheduleItem setTeacherName(String TEACHER_NAME) {
        this.TEACHER_NAME = TEACHER_NAME;
        return this;
    }

    public int getLessonWeek() {
        return LESSON_WEEK;
    }

    public ScheduleItem setLessonWeek(int LESSON_WEEK) {
        this.LESSON_WEEK = LESSON_WEEK;
        return this;
    }

    public String getTimeStart() {
        return TIME_START;
    }

    public ScheduleItem setTimeStart(String TIME_START) {
        this.TIME_START = TIME_START;
        return this;
    }

    public String getTimeEnd() {
        return TIME_END;
    }

    public ScheduleItem setTimeEnd(String TIME_END) {
        this.TIME_END = TIME_END;
        return this;
    }

    @Override
    public String toString() {
        return LESSON_NAME + " + " + TEACHER_NAME;
    }

    public boolean isDevider() {
        return DEVIDER;
    }

    public void setDevider(boolean DEVIDER) {
        this.DEVIDER = DEVIDER;
    }
}
