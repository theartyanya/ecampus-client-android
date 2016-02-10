package com.kpi.campus.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.kpi.campus.database.SqlHelper;
import com.kpi.campus.model.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 09.02.2016.
 */
public class TokenTable {

    public static final Uri URI = SqlHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, @NonNull Token airport) {
        context.getContentResolver().insert(URI, toContentValues(airport));
    }

    public static void save(Context context, @NonNull List<Token> airports) {
        ContentValues[] values = new ContentValues[airports.size()];
        for (int i = 0; i < airports.size(); i++) {
            values[i] = toContentValues(airports.get(i));
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull Token airport) {
        ContentValues values = new ContentValues();
        values.put(Columns.ACCESS_TOKEN, airport.getAccessToken());
        values.put(Columns.TOKEN_TYPE, airport.getTokenType());
        values.put(Columns.EXPIRES_IN, airport.getExpiresIn());
        return values;
    }

    @NonNull
    public static Token fromCursor(@NonNull Cursor cursor) {
        String iata = cursor.getString(cursor.getColumnIndex(Columns.ACCESS_TOKEN));
        String name = cursor.getString(cursor.getColumnIndex(Columns.TOKEN_TYPE));
        String airportName = cursor.getString(cursor.getColumnIndex(Columns.EXPIRES_IN));
        return new Token(iata, name, airportName);
    }

    @NonNull
    public static List<Token> listFromCursor(@NonNull Cursor cursor) {
        List<Token> airports = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return airports;
        }
        try {
            do {
                airports.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return airports;
        } finally {
            cursor.close();
        }
    }

    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }

    public interface Columns {
        String ACCESS_TOKEN = "token";
        String TOKEN_TYPE = "type";
        String EXPIRES_IN = "expires";
    }

    public interface Requests {

        String TABLE_NAME = TokenTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                Columns.ACCESS_TOKEN + " VARCHAR, " +
                Columns.TOKEN_TYPE + " VARCHAR(50), " +
                Columns.EXPIRES_IN + " VARCHAR(50)" + ");";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
