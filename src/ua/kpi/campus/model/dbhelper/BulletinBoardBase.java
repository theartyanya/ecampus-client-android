package ua.kpi.campus.model.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ua.kpi.campus.model.BulletinBoardSubject;
import ua.kpi.campus.model.TimetableElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public class BulletinBoardBase extends DatabaseHelper {
    public BulletinBoardBase(Context context) {
        super(context);
    }

    public int createTimetableLesson(BulletinBoardSubject bulletinBoardSubject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BulletinBoardEntry.KEY_BULLETIN_BOARD_ID, bulletinBoardSubject.getBulletinBoardId());
        values.put(BulletinBoardEntry.KEY_DATE_CREATE, bulletinBoardSubject.getDateCreate());
        values.put(BulletinBoardEntry.KEY_CREATOR_ID, bulletinBoardSubject.getCreatorUserAccountId());
        values.put(BulletinBoardEntry.KEY_CREATOR_NAME, bulletinBoardSubject.getCreatorUserAccountFullname());
        values.put(BulletinBoardEntry.KEY_SUBJECT_ID, bulletinBoardSubject.getBulletinBoardSubjectId());
        values.put(BulletinBoardEntry.KEY_BULLETIN_BOARD_ID, bulletinBoardSubject.getBulletinBoardId());
        values.put(BulletinBoardEntry.KEY_LINK_RECEPIENTS, bulletinBoardSubject.getBulletinBoardLinkRecipients());
        values.put(BulletinBoardEntry.KEY_SUBJECT, bulletinBoardSubject.getSubject());
        Log.d(TAG, hashCode() + " adding BB_Suject id " + bulletinBoardSubject.getBulletinBoardId());
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
