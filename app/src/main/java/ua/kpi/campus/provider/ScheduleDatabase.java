package ua.kpi.campus.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import ua.kpi.campus.provider.ScheduleContract.*;

/**
 * Created by doroshartyom on 08.01.2015.
 */
public class ScheduleDatabase extends SQLiteOpenHelper {

    private static final String LOG_TAG = "ScheduleDatabase";
    private static final String DATABASE_NAME = "schedule.db";

    private final Context mContext;

    interface Tables {
        String SCHEDULE = "schedule";
        String TEACHERS = "teachers";
        String SCHEDULE_TEACHER="schedule_teacher";
    }
    public ScheduleDatabase(Context context) {
        super(context, "database", null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating Schedule table
        db.execSQL("CREATE TABLE " + Tables.SCHEDULE + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ScheduleColumns.LESSON_ID + " INTEGER NOT NULL,"
            + ScheduleColumns.DAY_NUMBER + " INTEGER NOT NULL,"
            + ScheduleColumns.LESSON_NUMBER + " INTEGER NOT NULL,"
            + ScheduleColumns.LESSON_NAME + " TEXT,"
            + ScheduleColumns.LESSON_ROOM + " TEXT,"
            + ScheduleColumns.TEACHER_NAME + " TEXT,"
            + ScheduleColumns.TEACHER_ID + " INTEGER,"
            + ScheduleColumns.LESSON_WEEK + " INTEGER,"
            + ScheduleColumns.TIME_START + " TEXT,"
            + ScheduleColumns.TIME_END + " TEXT,"
            +"UNIQUE (" + ScheduleColumns.LESSON_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE " + Tables.TEACHERS + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TeacherColumns.TEACHER_ID + " INTEGER NOT NULL,"
            + TeacherColumns.TEACHER_NAME + " TEXT,"
            + TeacherColumns.TEACHER_SUBJECT + " TEXT,"
            +"UNIQUE (" + TeacherColumns.TEACHER_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE " + Tables.SCHEDULE_TEACHER + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScheduleColumns.LESSON_ID + " INTEGER NOT NULL,"
                + ScheduleColumns.DAY_NUMBER + " INTEGER NOT NULL,"
                + ScheduleColumns.LESSON_NUMBER + " INTEGER NOT NULL,"
                + ScheduleColumns.LESSON_NAME + " TEXT,"
                + ScheduleColumns.LESSON_ROOM + " TEXT,"
                + ScheduleColumns.TEACHER_NAME + " TEXT,"
                + ScheduleColumns.TEACHER_ID + " INTEGER,"
                + ScheduleColumns.LESSON_WEEK + " INTEGER,"
                + ScheduleColumns.TIME_START + " TEXT,"
                + ScheduleColumns.TIME_END + " TEXT,"
                + ScheduleColumns.GROUP_ID +" INTEGER,"
                + ScheduleColumns.GROUP_NAME +" TEXT,"
                +"UNIQUE (" + ScheduleColumns.LESSON_ID + ") ON CONFLICT REPLACE)");

        Log.d(LOG_TAG, "Databases created");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Tables.SCHEDULE);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.TEACHERS);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.SCHEDULE_TEACHER);

            onCreate(db);
        }

    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
