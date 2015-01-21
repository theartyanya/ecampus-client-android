package ua.kpi.campus.provider;

/**
 * Created by doroshartyom on 08.01.2015.
 */
public class ScheduleContract {

    interface ScheduleColumns {
        String LESSON_ID = "lesson_id";
        String DAY_NUMBER = "day_number";
        String LESSON_NUMBER = "lesson_number";
        String LESSON_NAME = "lesson_name";
        String LESSON_ROOM = "lesson_room";
        String TEACHER_NAME = "teacher_name";
        String TEACHER_ID = "teacher_id";
        String LESSON_WEEK = "lesson_week";
        String TIME_START = "time_start";
        String TIME_END = "time_end";
        String GROUP_ID="group_id";
        String GROUP_NAME="group_name";

    }

    interface TeacherColumns {
        String TEACHER_ID = "teacher_id";
        String TEACHER_NAME = "teacher_name";
        String TEACHER_SUBJECT = "teacher_subject";
    }
}
