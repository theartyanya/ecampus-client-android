package ua.kpi.campus.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ua.kpi.campus.model.ScheduleItem;
import ua.kpi.campus.provider.ScheduleContract.*;
import ua.kpi.campus.ui.ScheduleAdapter;

/**
 * Created by doroshartyom on 08.01.2015.
 */
public class ScheduleProvider {

    private SQLiteDatabase db;
    private ScheduleDatabase scheduleDatabase;
    private ContentValues cv;

    private Context mContext;
    private String LOG_TAG = "ScheduleProvider";
    private String SCHEDULE = "schedule";

    private int lastday = 0;

    public ScheduleProvider(Context context) {
        Log.d(LOG_TAG, "Creating database");

        scheduleDatabase = new ScheduleDatabase(context);

        db = scheduleDatabase.getWritableDatabase();

        if (db!=null)
            Log.d(LOG_TAG, "All os OK");
    }

    //Adding ScheduleItem to database
    public void addToScheduleDatabase(ScheduleItem item){
        //Checking is ScheduleItem is not null
        if (item != null) {
            cv = new ContentValues();

            Log.d(LOG_TAG, "Putting ContentValues");

            //Adding content values
            cv.put(ScheduleColumns.LESSON_ID, item.getLessonId());
            cv.put(ScheduleColumns.DAY_NUMBER, item.getDayNumber());
            cv.put(ScheduleColumns.LESSON_NUMBER, item.getLessonNumber());
            cv.put(ScheduleColumns.LESSON_NAME, item.getLessonName());
            cv.put(ScheduleColumns.LESSON_ROOM, item.getLessonRoom());
            cv.put(ScheduleColumns.TEACHER_NAME, item.getTeacherName());
            cv.put(ScheduleColumns.LESSON_WEEK, item.getLessonWeek());
            cv.put(ScheduleColumns.TIME_START, item.getTimeStart());
            cv.put(ScheduleColumns.TIME_END, item.getTimeEnd());

            db.insert(SCHEDULE, null, cv);
        }
    }

    //Returns Item from ScheduleDatabase
    public ArrayList<ScheduleItem> getScheduleItemsFromDatabase(int week) {
        ArrayList<ScheduleItem> items = new ArrayList<ScheduleItem>();
        Cursor cursor = db.query(SCHEDULE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int lesson_id = cursor.getColumnIndex(ScheduleColumns.LESSON_ID);
            int day_number = cursor.getColumnIndex(ScheduleColumns.DAY_NUMBER);
            int lesson_number = cursor.getColumnIndex(ScheduleColumns.LESSON_NUMBER);
            int lesson_name = cursor.getColumnIndex(ScheduleColumns.LESSON_NAME);
            int lesson_room = cursor.getColumnIndex(ScheduleColumns.LESSON_ROOM);
            int teacher_name = cursor.getColumnIndex(ScheduleColumns.TEACHER_NAME);
            int lesson_week = cursor.getColumnIndex(ScheduleColumns.LESSON_WEEK);
            int time_start = cursor.getColumnIndex(ScheduleColumns.TIME_START);
            int time_end = cursor.getColumnIndex(ScheduleColumns.TIME_END);


             do {
                 ScheduleItem item = new ScheduleItem();
                 ScheduleItem deviderItem = new ScheduleItem();

                 if (cursor.getInt(lesson_week) == week) {

                     if (cursor.getInt(day_number) != lastday) {
                         lastday = cursor.getInt(day_number);

                         Log.d(LOG_TAG, "Adding divider for day #"+ lastday);

                         deviderItem.setLessonId(0)
                                 .setDayNumber(lastday)
                                 .setLessonNumber(0)
                                 .setLessonName("")
                                 .setLessonRoom("")
                                 .setTeacherName("")
                                 .setLessonWeek(week)
                                 .setTimeStart("")
                                 .setTimeEnd("")
                                 .setDevider(true);


                         items.add(deviderItem);
                     }
                     item.setLessonId(cursor.getInt(lesson_id))
                             .setDayNumber(cursor.getInt(day_number))
                             .setLessonNumber(cursor.getInt(lesson_number))
                             .setLessonName(cursor.getString(lesson_name))
                             .setLessonRoom(cursor.getString(lesson_room))
                             .setTeacherName(cursor.getString(teacher_name))
                             .setLessonWeek(cursor.getInt(lesson_week))
                             .setTimeStart(cursor.getString(time_start))
                             .setTimeEnd(cursor.getString(time_end))
                             .setDevider(false);

                     items.add(item);
                 }
             } while (cursor.moveToNext());
        }
        return items;
    }

    public void clear() {
        db.delete(SCHEDULE, null, null);
    }
}
