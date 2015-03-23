package com.example.krishnateja.junittestingandcontentproviders.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.krishnateja.junittestingandcontentproviders.data.TablesContract;

/**
 * Created by krishnateja on 3/22/2015.
 */
public class FriendsDataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "friendship";
    public static final int DATABASE_VERSION = 1;

    public FriendsDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + TablesContract.User.TABLE_NAME + " (" +
                TablesContract.User._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TablesContract.User.COLUMN_USER_NAME + " TEXT NOT NULL UNIQUE" +
                " );";
        final String SQL_CREATE_FRIENDS_TABLE = "CREATE TABLE " + TablesContract.Friends.TABLE_NAME + " (" +
                TablesContract.Friends._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TablesContract.Friends.COLUMN_USER_KEY + " INTEGER NOT NULL, " +
                TablesContract.Friends.COLUMN_FRIEND_NAME + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + TablesContract.Friends.COLUMN_USER_KEY + " ) REFERENCES " +
                TablesContract.User.TABLE_NAME + " (" + TablesContract.User._ID + " )" +
                ");";
        db.execSQL(SQL_CREATE_FRIENDS_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TablesContract.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TablesContract.Friends.TABLE_NAME);
        onCreate(db);
    }
}
