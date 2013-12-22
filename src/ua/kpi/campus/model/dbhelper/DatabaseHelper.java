package ua.kpi.campus.model.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ua.kpi.campus.api.jsonparsers.message.UserMessage;
import ua.kpi.campus.model.Conversation;
import ua.kpi.campus.model.Message;
import ua.kpi.campus.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/21/13
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String TAG = DatabaseHelper.class.getName();
    private static int DATABASE_VERSION = 7;
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
    private static final String KEY_CONVERSATIONS_SUBJECT = "group_subject";
    private static final String KEY_CONVERSATIONS_LAST_MESSAGE_TEXT = "last_message_text";
    private static final String KEY_CONVERSATIONS_LAST_MESSAGE_DATE = "last_message_date";

    //TABLE_MESSAGES
    private static final String KEY_MESSAGES_ID = "message_id";
    private static final String KEY_MESSAGES_DATE_SENT = "date_sent";
    private static final String KEY_MESSAGES_TEXT = "text";
    private static final String KEY_MESSAGES_SUBJECT = "subject";
    //SQL Statements
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String INTEGER = " INTEGER ";
    private static final String TEXT = " TEXT ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String FOREIGN_KEY = " FOREIGN KEY ";
    private static final String REFERENCES = " REFERENCES ";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    private static final String SELECT_SELECT_FROM_S_WHERE_S = "SELECT * FROM %s WHERE %s";
    // Table Create Statements
    private static final String CREATE_TABLE_USERS =
            CREATE_TABLE + TABLE_USERS + "(" +
                    KEY_USER_ACCOUN_ID + INTEGER + "," +
                    KEY_USER_FULLNAME + TEXT + "," +
                    KEY_USER_PHOTO + TEXT + "," +
                    PRIMARY_KEY + "(" + KEY_USER_ACCOUN_ID + ")" + ")";
    private static final String CREATE_TABLE_CONVERSATIONS =
            CREATE_TABLE + TABLE_CONVERSATIONS + "(" +
                    KEY_CONVERSATIONS_GROUP_ID + INTEGER + "," +
                    KEY_CONVERSATIONS_SUBJECT + TEXT + "," +
                    KEY_CONVERSATIONS_LAST_MESSAGE_TEXT + TEXT + "," +
                    KEY_CONVERSATIONS_LAST_MESSAGE_DATE + INTEGER + "," +
                    PRIMARY_KEY + "(" + KEY_CONVERSATIONS_GROUP_ID + ")" + ")";
    ;
    private static final String CREATE_TABLE_CONVERSATIONS_USERS =
            CREATE_TABLE + TABLE_CONVERSATIONS_USERS + "(" +
                    KEY_CONVERSATIONS_GROUP_ID + INTEGER + "," +
                    KEY_USER_ACCOUN_ID + INTEGER + "," +
                    FOREIGN_KEY + "(" + KEY_CONVERSATIONS_GROUP_ID + ")" +
                    REFERENCES + TABLE_CONVERSATIONS + "(" + KEY_CONVERSATIONS_GROUP_ID + ")" +
                    FOREIGN_KEY + "(" + KEY_USER_ACCOUN_ID + ")" +
                    REFERENCES + TABLE_USERS + "(" + KEY_USER_ACCOUN_ID + ")" + ")";
    private static final String CREATE_TABLE_MESSAGES =
            CREATE_TABLE + TABLE_MESSAGES + "(" +
                    KEY_MESSAGES_ID + INTEGER + "," +
                    KEY_CONVERSATIONS_GROUP_ID + INTEGER + "," +
                    KEY_MESSAGES_DATE_SENT + INTEGER + "," +
                    KEY_USER_ACCOUN_ID + INTEGER + "," +
                    KEY_MESSAGES_TEXT + TEXT + "," +
                    KEY_MESSAGES_SUBJECT + TEXT + "," +
                    PRIMARY_KEY + "(" + KEY_MESSAGES_ID + ")" + ")";
    //   FOREIGN_KEY + "(" + KEY_CONVERSATIONS_GROUP_ID + ")" +
    //   REFERENCES + TABLE_CONVERSATIONS + "(" + KEY_CONVERSATIONS_GROUP_ID + ")"


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, hashCode() + " instance of db " + DATABASE_NAME + "created.");
    }

    public DatabaseHelper(Context context, int userId) {
        super(context, DATABASE_NAME, null, userId);
        DATABASE_VERSION = userId;
        Log.d(TAG, hashCode() + " instance of db " + DATABASE_NAME + "created.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, hashCode() + " creating db. Path " + db.getPath());
        db.execSQL(CREATE_TABLE_CONVERSATIONS);
        Log.d(TAG, hashCode() + " SQL query: " + CREATE_TABLE_CONVERSATIONS);
        db.execSQL(CREATE_TABLE_USERS);
        Log.d(TAG, hashCode() + " SQL query: " + CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CONVERSATIONS_USERS);
        Log.d(TAG, hashCode() + " SQL query: " + CREATE_TABLE_CONVERSATIONS_USERS);
        db.execSQL(CREATE_TABLE_MESSAGES);
        Log.d(TAG, hashCode() + " SQL query: " + CREATE_TABLE_MESSAGES);

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

    public String getPath() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.getPath();
    }

    /*
     * TABLE_USERS
     */
    private int createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USER_ACCOUN_ID, user.getId());
        values.put(KEY_USER_FULLNAME, user.getFullname());
        values.put(KEY_USER_PHOTO, user.getPhoto());
        int user_id = (int) db.insert(TABLE_USERS, null, values);

        return user_id;
    }

    public void addAllUsers(Set<UserMessage> users) {
        Set<User> userSet = getAllUsersSet();
        for (UserMessage user : users) {
            if (!userSet.contains(user)) {
                createUser(new User(user)); }
        }
    }

    public User getUser(int id) throws IllegalArgumentException{
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = String.format(SELECT_SELECT_FROM_S_WHERE_S, TABLE_USERS, KEY_USER_ACCOUN_ID + " = " + Integer.toString(id));
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            c.moveToFirst();
        }

        if (c.getCount() == 0) {
            throw new IllegalArgumentException();
        }

        return new User(
                c.getInt(c.getColumnIndex(KEY_USER_ACCOUN_ID)),
                c.getString(c.getColumnIndex(KEY_USER_FULLNAME)),
                c.getString(c.getColumnIndex(KEY_USER_PHOTO)));
    }

    private List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            while (c.moveToNext()) {
                User user = new User(
                        c.getInt(c.getColumnIndex(KEY_USER_ACCOUN_ID)),
                        c.getString(c.getColumnIndex(KEY_USER_FULLNAME)),
                        c.getString(c.getColumnIndex(KEY_USER_PHOTO)));

                users.add(user);
            }
        }

        return users;
    }

    public Set<User> getAllUsersSet() {
        Set<User> users = new HashSet<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            while (c.moveToNext()) {
                User user = new User(
                        c.getInt(c.getColumnIndex(KEY_USER_ACCOUN_ID)),
                        c.getString(c.getColumnIndex(KEY_USER_FULLNAME)),
                        c.getString(c.getColumnIndex(KEY_USER_PHOTO)));

                users.add(user);
            }
        }

        return users;
    }

    /*
     * TABLE_CONVERSATIONS
     */
    public void refreshConversations() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_CONVERSATIONS);
        db.execSQL(CREATE_TABLE_CONVERSATIONS);
    }

    public int createConversation(Conversation conversation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CONVERSATIONS_GROUP_ID, conversation.getGroupId());
        values.put(KEY_CONVERSATIONS_SUBJECT, conversation.getSubject());
        values.put(KEY_CONVERSATIONS_LAST_MESSAGE_TEXT, conversation.getLastMessageText());
        values.put(KEY_CONVERSATIONS_LAST_MESSAGE_DATE, conversation.getLastMessageDate());
        return (int) db.insert(TABLE_CONVERSATIONS, null, values);
    }

    public List<Conversation> getAllConversations() {
        List<Conversation> conversations = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONVERSATIONS;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            while (c.moveToNext()) {
                Conversation conversation = new Conversation(
                        c.getInt(c.getColumnIndex(KEY_CONVERSATIONS_GROUP_ID)),
                        c.getString(c.getColumnIndex(KEY_CONVERSATIONS_SUBJECT)),
                        c.getString(c.getColumnIndex(KEY_CONVERSATIONS_SUBJECT)),
                        c.getLong(c.getColumnIndex(KEY_CONVERSATIONS_LAST_MESSAGE_TEXT)));

                conversations.add(conversation);
            }
        }

        return conversations;
    }

    /*
     * TABLE_MESSAGES
     */
    public int createMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_MESSAGES_ID, message.getMessageId());
        values.put(KEY_CONVERSATIONS_GROUP_ID, message.getGroupId());
        values.put(KEY_MESSAGES_DATE_SENT, message.getTimeSent());
        values.put(KEY_MESSAGES_SUBJECT, message.getSubject());
        values.put(KEY_MESSAGES_TEXT, message.getText());
        values.put(KEY_USER_ACCOUN_ID, message.getSenderId());
        return (int) db.insert(TABLE_USERS, null, values);
    }

    public List<Message> getLastMessages(int groupId) {
        List<Message> messages = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONVERSATIONS +
                " WHERE " + KEY_CONVERSATIONS_GROUP_ID + " = " + Integer.toString(groupId) +
                " ORDER BY " + KEY_MESSAGES_DATE_SENT;

        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            while (c.moveToNext()) {
                Message message = new Message(
                        c.getInt(c.getColumnIndex(KEY_MESSAGES_ID)),
                        c.getInt(c.getColumnIndex(KEY_CONVERSATIONS_GROUP_ID)),
                        c.getLong(c.getColumnIndex(KEY_MESSAGES_DATE_SENT)),
                        c.getString(c.getColumnIndex(KEY_MESSAGES_TEXT)),
                        c.getString(c.getColumnIndex(KEY_MESSAGES_SUBJECT)),
                        c.getInt(c.getColumnIndex(KEY_USER_ACCOUN_ID)));
                messages.add(message);
            }
        }


        return messages;
    }


}
