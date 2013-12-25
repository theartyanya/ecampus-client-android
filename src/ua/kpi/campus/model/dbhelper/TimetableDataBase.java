package ua.kpi.campus.model.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ua.kpi.campus.model.TimetableElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public class TimetableDataBase extends DatabaseHelper{
    public TimetableDataBase(Context context) {
        super(context);
    }

    public int createTimetableLesson(TimetableElement timetableElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TIMETABLE_LESSON_ID, timetableElement.getLessonId());
        values.put(KEY_TIMETABLE_EMPLOYEE, timetableElement.getEmployee());
        values.put(KEY_TIMETABLE_SUBJECT, timetableElement.getSubject());
        values.put(KEY_TIMETABLE_BUILDING, timetableElement.getBuilding());
        values.put(KEY_TIMETABLE_EMPLOYEE_PHOTO_PATH, timetableElement.getEmployeePhotoPath());
        values.put(KEY_TIMETABLE_WEEKEND_NUM, timetableElement.getWeekNum());
        values.put(KEY_TIMETABLE_DAY_ID, timetableElement.getDayId());
        values.put(KEY_TIMETABLE_DAY_NAME, timetableElement.getDayName());
        Log.d(TAG, hashCode() + " adding message id" + timetableElement.getLessonId());
        return (int) db.insert(TABLE_TIMETABLE, null, values);
    }

    public List<TimetableElement> getAllLessons() {
        List<TimetableElement> subjects = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TIMETABLE +
                " ORDER BY " + KEY_TIMETABLE_DAY_ID;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.getCount() == 0) {
            return subjects;
        }

        if (c.moveToFirst()) {
            do {
                TimetableElement timetableElement = new TimetableElement(
                        c.getInt(c.getColumnIndex(KEY_TIMETABLE_LESSON_ID)),
                        c.getString(c.getColumnIndex(KEY_TIMETABLE_EMPLOYEE)),
                        c.getString(c.getColumnIndex(KEY_TIMETABLE_SUBJECT)),
                        c.getString(c.getColumnIndex(KEY_TIMETABLE_BUILDING)),
                        c.getString(c.getColumnIndex(KEY_TIMETABLE_EMPLOYEE_PHOTO_PATH)),
                        c.getInt(c.getColumnIndex(KEY_TIMETABLE_WEEKEND_NUM)),
                        c.getInt(c.getColumnIndex(KEY_TIMETABLE_DAY_ID)),
                        c.getString(c.getColumnIndex(KEY_TIMETABLE_DAY_NAME)));
                        subjects.add(timetableElement);
            } while (c.moveToNext());
        }
        return subjects;
    }

}
