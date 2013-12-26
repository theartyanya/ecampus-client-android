package ua.kpi.campus.model.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ua.kpi.campus.api.jsonparsers.user.Employee;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public class EmployeeBase extends DatabaseHelper {
    private EmployeeBase(Context context) {
        super(context);
    }

    public static EmployeeBase getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final EmployeeBase INSTANCE = new EmployeeBase(mContext);
    }

    public int createEmployee(Employee employee, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EmployeeEntry.KEY_USER_ACCOUNT_ID, userId);
        values.put(EmployeeEntry.KEY_SUBDIVISION_ID, employee.getSubDivisionID());
        values.put(EmployeeEntry.KEY_SUBDIVISION_NAME, employee.getSubDivisionName());
        values.put(EmployeeEntry.KEY_POSITION, employee.getPosition());
        values.put(EmployeeEntry.KEY_ACADEMIC_DEGREE, employee.getAcademicDegree());
        values.put(EmployeeEntry.KEY_ACADEMIC_STATUS, employee.getAcademicStatus());
        Log.d(TAG, hashCode() + " adding createEmployee");
        return (int) db.insert(EmployeeEntry.TABLE_NAME, null, values);
    }

    public Employee getEmployee(int userId) {
        String selectQuery = "SELECT * FROM " + EmployeeEntry.TABLE_NAME +
                " WHERE " + EmployeeEntry.KEY_USER_ACCOUNT_ID + " = " + userId;
        Log.d(TAG, hashCode() + " SQL query: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Employee employee = null;
        if (c.moveToFirst()) {
            employee = new Employee(
                    c.getInt(c.getColumnIndex(EmployeeEntry.KEY_SUBDIVISION_ID)),
                    c.getString(c.getColumnIndex(EmployeeEntry.KEY_SUBDIVISION_NAME)),
                    c.getString(c.getColumnIndex(EmployeeEntry.KEY_POSITION)),
                    c.getString(c.getColumnIndex(EmployeeEntry.KEY_ACADEMIC_DEGREE)),
                    c.getString(c.getColumnIndex(EmployeeEntry.KEY_ACADEMIC_STATUS)));
        }

        return employee;
    }
}
