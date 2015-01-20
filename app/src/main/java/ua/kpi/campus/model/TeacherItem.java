package ua.kpi.campus.model;

import java.io.Serializable;

/**
 * Created by doroshartyom on 15.01.2015.
 */
public class TeacherItem implements Serializable {
    
    private int TEACHER_ID;
    private String TEACHER_NAME;
    private String TEACHER_SUBJECT;

    public TeacherItem() {
    }

    public int getTeacherId() {
        return TEACHER_ID;
    }

    public TeacherItem setTeacherId(int TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
        return this;
    }

    public String getTeacherName() {
        return TEACHER_NAME;
    }

    public void setTeacherName(String TEACHER_NAME) {
        this.TEACHER_NAME = TEACHER_NAME;
    }
    
    @Override
    public String toString() {
        return "id: " + TEACHER_ID + "; name: " + TEACHER_NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeacherItem that = (TeacherItem) o;

        if (TEACHER_ID != that.TEACHER_ID) return false;
        if (!TEACHER_NAME.equals(that.TEACHER_NAME)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = TEACHER_ID;
        result = 31 * result + TEACHER_NAME.hashCode();
        return result;
    }

    public String getTeacherSubject() {
        return TEACHER_SUBJECT;
    }

    public TeacherItem setTeacherSubject(String TEACHER_SUBJECT) {
        this.TEACHER_SUBJECT = TEACHER_SUBJECT;
        return this;
    }
}
