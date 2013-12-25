package ua.kpi.campus.model.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ua.kpi.campus.model.BulletinBoardSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public class BulletinBoardBase extends DatabaseHelper {
    private static class LazyHolder {
        private static final BulletinBoardBase INSTANCE = new BulletinBoardBase(mContext);
    }

    public static BulletinBoardBase getInstance() {
        return LazyHolder.INSTANCE;
    }

    private BulletinBoardBase(Context context) {
        super(context);
    }

    private int createBulletin(BulletinBoardSubject bulletinBoardSubject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BulletinBoardEntry.KEY_TEXT, bulletinBoardSubject.getText());
        values.put(BulletinBoardEntry.KEY_DATE_CREATE, bulletinBoardSubject.getDateCreate());
        values.put(BulletinBoardEntry.KEY_CREATOR_ID, bulletinBoardSubject.getCreatorUserAccountId());
        values.put(BulletinBoardEntry.KEY_CREATOR_NAME, bulletinBoardSubject.getCreatorUserAccountFullname());
        values.put(BulletinBoardEntry.KEY_SUBJECT_ID, bulletinBoardSubject.getBulletinBoardSubjectId());
        values.put(BulletinBoardEntry.KEY_BULLETIN_BOARD_ID, bulletinBoardSubject.getBulletinBoardId());
        values.put(BulletinBoardEntry.KEY_LINK_RECEPIENTS, bulletinBoardSubject.getBulletinBoardLinkRecipients());
        values.put(BulletinBoardEntry.KEY_SUBJECT, bulletinBoardSubject.getSubject());
        Log.d(TAG, hashCode() + " adding BB_Suject id " + bulletinBoardSubject.getBulletinBoardId());
        return (int) db.insert(BulletinBoardEntry.TABLE_NAME, null, values);
    }

    public List<BulletinBoardSubject> getActualBulletins() {
        List<BulletinBoardSubject> boardSubjects = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TIMETABLE +
                " ORDER BY " + KEY_TIMETABLE_DAY_ID;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.getCount() == 0) {
            return boardSubjects;
        }

        if (c.moveToFirst()) {
            do {
                BulletinBoardSubject boardSubject = new BulletinBoardSubject(
                        c.getString(c.getColumnIndex(BulletinBoardEntry.KEY_TEXT)),
                        c.getLong(c.getColumnIndex(BulletinBoardEntry.KEY_DATE_CREATE)),
                        c.getInt(c.getColumnIndex(BulletinBoardEntry.KEY_CREATOR_ID)),
                        c.getString(c.getColumnIndex(BulletinBoardEntry.KEY_CREATOR_NAME)),
                        c.getInt(c.getColumnIndex(BulletinBoardEntry.KEY_SUBJECT_ID)),
                        c.getInt(c.getColumnIndex(BulletinBoardEntry.KEY_BULLETIN_BOARD_ID)),
                        c.getString(c.getColumnIndex(BulletinBoardEntry.KEY_LINK_RECEPIENTS)),
                        c.getString(c.getColumnIndex(BulletinBoardEntry.KEY_SUBJECT)));
                boardSubjects.add(boardSubject);
            } while (c.moveToNext());
        }
        return boardSubjects;
    }

    public void addAllBulletins(List<BulletinBoardSubject> subjects) {
        Log.d(TAG, hashCode() + " adding started.");
        for (BulletinBoardSubject subject : subjects) {
            createBulletin(subject);
        }
        Log.d(TAG, hashCode() + " adding finished.");
    }
}
