package ua.kpi.campus.model.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ua.kpi.campus.api.jsonparsers.user.Personality;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public class PeronalitiesBase extends DatabaseHelper {
    private PeronalitiesBase(Context context) {
        super(context);
    }

    public static PeronalitiesBase getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final PeronalitiesBase INSTANCE = new PeronalitiesBase(mContext);
    }

    public int createPersonality(Personality personality, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PersonalitiesEntry.KEY_USER_ACCOUNT_ID, userId);
        values.put(PersonalitiesEntry.KEY_SUBDIVISION_ID, personality.getSubdivisionID());
        values.put(PersonalitiesEntry.KEY_SUBDIVISION_NAME, personality.getSubdivisionName());
        values.put(PersonalitiesEntry.KEY_IS_CONTRACT, Boolean.toString(personality.isContract()));
        values.put(PersonalitiesEntry.KEY_SPECIALITY, personality.getSpeciality());
        values.put(PersonalitiesEntry.KEY_STUDY_GROUP_NAME, personality.getStudyGroupName());
        Log.d(TAG, hashCode() + " adding createEmployee");
        return (int) db.insert(PersonalitiesEntry.TABLE_NAME, null, values);
    }

    public Personality getPersonality(int userId) {
        String selectQuery = "SELECT * FROM " + PersonalitiesEntry.TABLE_NAME +
                " WHERE " + PersonalitiesEntry.KEY_USER_ACCOUNT_ID + " = " + userId;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Personality personality = null;
        if (c.moveToFirst()) {
            personality = new Personality(
                    c.getInt(c.getColumnIndex(PersonalitiesEntry.KEY_SUBDIVISION_ID)),
                    c.getString(c.getColumnIndex(PersonalitiesEntry.KEY_SUBDIVISION_NAME)),
                    c.getString(c.getColumnIndex(PersonalitiesEntry.KEY_STUDY_GROUP_NAME)),
                    Boolean.valueOf(c.getString(c.getColumnIndex(PersonalitiesEntry.KEY_IS_CONTRACT))),
                    c.getString(c.getColumnIndex(PersonalitiesEntry.KEY_SPECIALITY)));
        }

        return personality;
    }

}
