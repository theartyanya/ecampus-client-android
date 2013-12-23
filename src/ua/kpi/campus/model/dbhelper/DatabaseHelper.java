package ua.kpi.campus.model.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ua.kpi.campus.api.jsonparsers.message.UserMessage;
import ua.kpi.campus.model.Conversation;
import ua.kpi.campus.model.CurrentUser;
import ua.kpi.campus.model.Message;
import ua.kpi.campus.model.User;

import java.io.Closeable;
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
public class DatabaseHelper extends SQLiteOpenHelper implements Closeable {
    public final static String TAG = DatabaseHelper.class.getName();
    private static final int MESSAGES_SET_CAPACITY = 100;
    private static final int DATABASE_VERSION = 21;
    private static final String DATABASE_NAME = "eCampus";
    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CONVERSATIONS_USERS = "conversations_users";
    private static final String TABLE_CONVERSATIONS = "conversations";
    private static final String TABLE_MESSAGES = "messages";
    private static final String TABLE_CURRENT_USER = "current_user";
    // TABLE_USERS - column names
    private static final String KEY_USER_ACCOUN_ID = "user_account_id";
    private static final String KEY_USER_FULLNAME = "fullname";
    private static final String KEY_USER_PHOTO = "photo";
    //TABLE_CONVERSATIONS
    private static final String KEY_CONVERSATIONS_GROUP_ID = "group_id";
    private static final String KEY_CONVERSATIONS_SUBJECT = "group_subject";
    private static final String KEY_CONVERSATIONS_LAST_MESSAGE_TEXT = "last_message_text";
    private static final String KEY_CONVERSATIONS_LAST_MESSAGE_DATE = "last_message_date";
    //TABLE_CURRENT_USER
    private static final String KEY_CURRENT_USER_ID = "user_id";
    private static final String KEY_CURRENT_USER_SESSION_ID = "session_id";
    private static final String KEY_CURRENT_USER_LOGIN = "login";
    private static final String KEY_CURRENT_USER_PASSWORD = "password";
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
    private static final String CREATE_TABLE_CURRENT_USER =
            CREATE_TABLE + TABLE_CURRENT_USER + "(" +
                    KEY_CURRENT_USER_ID + INTEGER + "," +
                    KEY_CURRENT_USER_SESSION_ID + TEXT + "," +
                    KEY_CURRENT_USER_LOGIN + TEXT + "," +
                    KEY_CURRENT_USER_PASSWORD + TEXT + "," +
                    PRIMARY_KEY + "(" + KEY_CURRENT_USER_ID + ")" + ")";
    private static final String CREATE_TABLE_CONVERSATIONS =
            CREATE_TABLE + TABLE_CONVERSATIONS + "(" +
                    KEY_CONVERSATIONS_GROUP_ID + INTEGER + "," +
                    KEY_CONVERSATIONS_SUBJECT + TEXT + "," +
                    KEY_CONVERSATIONS_LAST_MESSAGE_TEXT + TEXT + "," +
                    KEY_CONVERSATIONS_LAST_MESSAGE_DATE + INTEGER + "," +
                    PRIMARY_KEY + "(" + KEY_CONVERSATIONS_GROUP_ID + ")" + ")";
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
        Log.d(TAG, hashCode() + " instance of db " + DATABASE_NAME + DATABASE_VERSION + " created.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, hashCode() + " creating db. Path " + db.getPath());
        db.execSQL(CREATE_TABLE_CURRENT_USER);
        Log.d(TAG, hashCode() + " SQL query: " + CREATE_TABLE_CURRENT_USER);
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
        dropAllTables(db);
        onCreate(db);
    }

    public String getPath() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.getPath();
    }

    private void dropAllTables(SQLiteDatabase db) {
        // on upgrade drop older tables
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_CURRENT_USER);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_CONVERSATIONS);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_USERS);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_CONVERSATIONS_USERS);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_MESSAGES);
        Log.d(TAG, hashCode() + " tables dropped.");
    }

    public void onUserChanged(CurrentUser currentUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        dropAllTables(db);
        onCreate(db);
        setCurrentUser(currentUser);
    }

    public CurrentUser getCurrentUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CURRENT_USER;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount() == 0) {
            return null;
        }

        if (c != null) {
            c.moveToFirst();
            return new CurrentUser(
                    c.getInt(c.getColumnIndex(KEY_CURRENT_USER_ID)),
                    c.getString(c.getColumnIndex(KEY_CURRENT_USER_SESSION_ID)),
                    c.getString(c.getColumnIndex(KEY_CURRENT_USER_LOGIN)),
                    c.getString(c.getColumnIndex(KEY_CURRENT_USER_PASSWORD)));
        } else return null;
    }

    private void setCurrentUser(CurrentUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CURRENT_USER_ID, user.getId());
        values.put(KEY_CURRENT_USER_SESSION_ID, user.getSessionId());
        values.put(KEY_CURRENT_USER_LOGIN, user.getLogin());
        values.put(KEY_CURRENT_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_CURRENT_USER, null, values);
    }

    //TODO change this method to update login and password
    public void updateCurrentUser(CurrentUser user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CURRENT_USER_SESSION_ID, user.getSessionId());

        // updating row
        db.update(TABLE_CURRENT_USER, values, KEY_CURRENT_USER_SESSION_ID + " = ?",
                new String[]{String.valueOf(user.getSessionId())});
        Log.d(TAG, hashCode() + " sessionId updated");
    }



    public String getSessionId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_CURRENT_USER_SESSION_ID + " FROM " + TABLE_CURRENT_USER;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            c.moveToFirst();
        }

        return c.getString(c.getColumnIndex(KEY_CURRENT_USER_SESSION_ID));

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
                createUser(new User(user));
            }
        }
    }

    public User getUser(int id) throws IllegalArgumentException {
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

        if(c.getCount() == 0) {
            return users;
        }

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User user = new User(
                        c.getInt(c.getColumnIndex(KEY_USER_ACCOUN_ID)),
                        c.getString(c.getColumnIndex(KEY_USER_FULLNAME)),
                        c.getString(c.getColumnIndex(KEY_USER_PHOTO)));

                users.add(user);
            } while (c.moveToNext());
        }

        return users;
    }

    public Set<User> getAllUsersSet() {
        Set<User> users = new HashSet<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.getCount() == 0) {
            return users;
        }

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User user = new User(
                        c.getInt(c.getColumnIndex(KEY_USER_ACCOUN_ID)),
                        c.getString(c.getColumnIndex(KEY_USER_FULLNAME)),
                        c.getString(c.getColumnIndex(KEY_USER_PHOTO)));

                users.add(user);
            } while (c.moveToNext());
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
        String selectQuery = "SELECT  * FROM " + TABLE_CONVERSATIONS +
                " ORDER BY " + KEY_CONVERSATIONS_LAST_MESSAGE_DATE + " DESC";
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.getCount() == 0) {
            return conversations;
        }
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Conversation conversation = new Conversation(
                        c.getInt(c.getColumnIndex(KEY_CONVERSATIONS_GROUP_ID)),
                        c.getString(c.getColumnIndex(KEY_CONVERSATIONS_SUBJECT)),
                        c.getString(c.getColumnIndex(KEY_CONVERSATIONS_LAST_MESSAGE_TEXT)),
                        c.getLong(c.getColumnIndex(KEY_CONVERSATIONS_LAST_MESSAGE_DATE)));

                conversations.add(conversation);
            } while (c.moveToNext());
        }

        return conversations;
    }

    /*
     * TABLE_MESSAGES
     */
    private int createMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_MESSAGES_ID, message.getMessageId());
        values.put(KEY_CONVERSATIONS_GROUP_ID, message.getGroupId());
        values.put(KEY_MESSAGES_DATE_SENT, message.getTimeSent());
        values.put(KEY_USER_ACCOUN_ID, message.getSenderId());
        values.put(KEY_MESSAGES_TEXT, message.getText());
        values.put(KEY_MESSAGES_SUBJECT, message.getSubject());
        return (int) db.insert(TABLE_MESSAGES, null, values);
    }

    public void addAllMessages(Set<Message> messages, int groupId) {
        Set<Message> messageSet = getLastMessages(groupId);
        for (Message message : messages) {
            if (!messageSet.contains(message)) {
                createMessage(message);
            }
        }
    }

    public Set<Message> getLastMessages(int groupId) {
        Set<Message> messages = new HashSet<>(MESSAGES_SET_CAPACITY);
        String selectQuery = "SELECT * FROM " + TABLE_MESSAGES +
                " WHERE " + KEY_CONVERSATIONS_GROUP_ID + " = " + Integer.toString(groupId) +
                " ORDER BY " + KEY_MESSAGES_DATE_SENT;

        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.getCount() == 0) {
            return messages;
        }

        if (c.moveToFirst()) {
           do {
                Message message = new Message(
                        c.getInt(c.getColumnIndex(KEY_MESSAGES_ID)),
                        c.getInt(c.getColumnIndex(KEY_CONVERSATIONS_GROUP_ID)),
                        c.getLong(c.getColumnIndex(KEY_MESSAGES_DATE_SENT)),
                        c.getString(c.getColumnIndex(KEY_MESSAGES_TEXT)),
                        c.getString(c.getColumnIndex(KEY_MESSAGES_SUBJECT)),
                        c.getInt(c.getColumnIndex(KEY_USER_ACCOUN_ID)));
                messages.add(message);
            } while (c.moveToNext());
        }

        return messages;
    }


}
