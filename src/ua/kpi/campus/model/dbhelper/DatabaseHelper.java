package ua.kpi.campus.model.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/21/13
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public final static String TAG = DatabaseHelper.class.getName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eCampus";

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CONVERSATIONS_USERS = "conversations_users";
    private static final String TABLE_CONVERSATIONS = "conversations";
    private static final String TABLE_MESSAGES = "messages";

    // TABLE_USERS - column names
    private static final String KEY_USER_ACCOUN_ID = "user_account_id";
    private static final String KEY_USER_FULLNAME = "fullname";
    private static final String KEY_USER_PHOTO = "photo";

    //TABLE_CONVERSATIONS
    private static final String KEY_CONVERSATIONS_GROUP_ID = "group_id";
    private static final String KEY_CONVERSATIONS_SUBJECT = "grup_subject";
    private static final String KEY_CONVERSATIONS_LAST_MESSAGE_ID = "last_message_id";

    //TABLE_MESSAGES
    private static final String KEY_MESSAGES_ID = "message_id";
    private static final String KEY_MESSAGES_DATE_SENT = "date_sent";
    private static final String KEY_MESSAGES_TEXT = "text";
    private static final String KEY_MESSAGES_SUBJECT = "subject";

    //SQL Statements
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String INTEGER = "INTEGER ";
    private static final String TEXT = "TEXT ";
    private static final String PRIMARY_KEY = "PRIMARY KEY ";
    private static final String FOREIGN_KEY = "FOREIGN KEY ";
    private static final String REFERENCES  = "REFERENCES ";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    // Table Create Statements
    private static final String CREATE_TABLE_USERS =
            CREATE_TABLE + TABLE_USERS + "(" +
                    KEY_USER_ACCOUN_ID + INTEGER + PRIMARY_KEY + "," +
                    KEY_USER_FULLNAME + TEXT + "," +
                    KEY_USER_PHOTO + TEXT + ")";

    private static final String CREATE_TABLE_CONVERSATIONS =
            CREATE_TABLE + TABLE_CONVERSATIONS + "(" +
                    KEY_CONVERSATIONS_GROUP_ID + INTEGER + PRIMARY_KEY + "," +
                    KEY_CONVERSATIONS_SUBJECT + TEXT + "," +
                    KEY_CONVERSATIONS_LAST_MESSAGE_ID + INTEGER + ")";

    private static final String CREATE_TABLE_CONVERSATIONS_USERS =
            CREATE_TABLE + TABLE_CONVERSATIONS_USERS + "(" +
                    KEY_CONVERSATIONS_GROUP_ID + INTEGER + "," +
                    KEY_USER_ACCOUN_ID + INTEGER + "," +
                    FOREIGN_KEY + "(" + KEY_CONVERSATIONS_GROUP_ID + ")" +
                    REFERENCES + TABLE_CONVERSATIONS + "(" + KEY_CONVERSATIONS_GROUP_ID + ")" +
                    FOREIGN_KEY + "(" + KEY_USER_ACCOUN_ID + ")" +
                    REFERENCES + TABLE_USERS + "(" + KEY_USER_ACCOUN_ID + ")" +")";

    private static final String CREATE_TABLE_MESSAGES =
            CREATE_TABLE + TABLE_CONVERSATIONS_USERS + "(" +
                    KEY_MESSAGES_ID + INTEGER + PRIMARY_KEY + "," +
                    KEY_CONVERSATIONS_GROUP_ID + INTEGER + "," +
                    KEY_MESSAGES_DATE_SENT + INTEGER + "," +
                    KEY_MESSAGES_TEXT + TEXT + "," +
                    KEY_MESSAGES_SUBJECT + TEXT +
                    FOREIGN_KEY + "(" + KEY_CONVERSATIONS_GROUP_ID + ")" +
                    REFERENCES + TABLE_CONVERSATIONS + "(" + KEY_CONVERSATIONS_GROUP_ID + ")" +")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, hashCode() + " instance of db " + DATABASE_NAME + "created.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONVERSATIONS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CONVERSATIONS_USERS);
        db.execSQL(CREATE_TABLE_MESSAGES);
        Log.d(TAG, hashCode() + " created tables.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.d(TAG, hashCode() + " updating db.");

        // on upgrade drop older tables
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_CONVERSATIONS);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_USERS);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_CONVERSATIONS_USERS);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_MESSAGES);
        Log.d(TAG, hashCode() + " tables dropped.");

        // create new tables
        onCreate(db);
    }
}
