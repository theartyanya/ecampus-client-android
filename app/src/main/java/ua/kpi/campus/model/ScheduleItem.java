package ua.kpi.campus.model;

import java.io.Serializable;

/**
 * Created by doroshartyom on 08.01.2015.
 */
public class ScheduleItem implements Serializable {

    protected int LESSON_ID;
    protected int DAY_NUMBER;
    protected int LESSON_NUMBER;
    protected String LESSON_NAME;
    protected String LESSON_ROOM;
    protected String TEACHER_NAME;
    protected int TEACHER_ID;
    protected int LESSON_WEEK;
    protected String TIME_START;
    protected String TIME_END;
    protected boolean DEVIDER;


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


    public int getTeacherId() {
        return TEACHER_ID;
    }

    public ScheduleItem setTeacherId(int TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
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
