package com.example.krishnateja.junittestingandcontentproviders;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.krishnateja.junittestingandcontentproviders.data.FriendsDataBaseHelper;
import com.example.krishnateja.junittestingandcontentproviders.data.TablesContract;

import java.util.HashSet;

/**
 * Created by krishnateja on 3/22/2015.
 */
public class TestDB extends AndroidTestCase {

    public static final String LOG_TAG = TestDB.class.getSimpleName();

    void deleteDatabase() {
        mContext.deleteDatabase(FriendsDataBaseHelper.DATABASE_NAME);
        //assertEquals(true,false);
    }

    @Override
    protected void setUp() throws Exception {
        deleteDatabase();
    }

    public void testCreateDB() throws Throwable {
        //assertEquals(false,true);
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(TablesContract.Friends.TABLE_NAME);
        tableNameHashSet.add(TablesContract.User.TABLE_NAME);
        mContext.deleteDatabase(FriendsDataBaseHelper.DATABASE_NAME);
        SQLiteDatabase db = new FriendsDataBaseHelper(this.mContext, FriendsDataBaseHelper.DATABASE_NAME, null, FriendsDataBaseHelper.DATABASE_VERSION).getWritableDatabase();
        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());
//        for (int i = 0; i < c.getCount(); i++) {
//            Log.d(LOG_TAG, c.getString(0));
//            Log.d(LOG_TAG,c.getColumnCount()+"column count");
//            c.moveToNext();
//        }
        //c.moveToFirst();
        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + TablesContract.User.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());
//        Log.d(LOG_TAG, "columncount->" + c.getColumnCount());
//        Log.d(LOG_TAG, "row count->" + c.getCount());
//        while (!c.isAfterLast()) {
//            for(int i=0;i<c.getColumnCount();i++){
//                Log.d(LOG_TAG,"column name->"+c.getColumnName(i));
//            }
//            Log.d(LOG_TAG,c.getString(1));
//            c.moveToNext();
//        }
        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> userTableColumns = new HashSet<String>();
        userTableColumns.add(TablesContract.User._ID);
        userTableColumns.add(TablesContract.User.COLUMN_USER_NAME);
        int columnNameIndex = c.getColumnIndex("name");

        do {
            String columnName = c.getString(columnNameIndex);
            userTableColumns.remove(columnName);
        } while (c.moveToNext());
        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                userTableColumns.isEmpty());
        db.close();
    }

    public void testUserTable() throws Throwable{

        insertUser();
    }
    public long insertUser(){
        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        SQLiteDatabase db = new FriendsDataBaseHelper(this.mContext, FriendsDataBaseHelper.DATABASE_NAME, null, FriendsDataBaseHelper.DATABASE_VERSION).getWritableDatabase();
        // Second Step: Create ContentValues of what you want to insert
        ContentValues testValues =new ContentValues();
        testValues.put(TablesContract.User.COLUMN_USER_NAME,"krishna");
        // Third Step: Insert ContentValues into database and get a row ID back
        long userRowId;
        userRowId = db.insert(TablesContract.User.TABLE_NAME, null, testValues);
        assertEquals(1,userRowId);
        db.close();
        return userRowId;
    }
    public void testFriendTable() throws Throwable{
        mContext.deleteDatabase(FriendsDataBaseHelper.DATABASE_NAME);
        long id=insertUser();
        SQLiteDatabase db = new FriendsDataBaseHelper(this.mContext, FriendsDataBaseHelper.DATABASE_NAME, null, FriendsDataBaseHelper.DATABASE_VERSION).getWritableDatabase();
        ContentValues testValues=new ContentValues();
        testValues.put(TablesContract.Friends.COLUMN_USER_KEY,id);
        testValues.put(TablesContract.Friends.COLUMN_FRIEND_NAME,"goutham");
        long friendRowId;
        friendRowId=db.insert(TablesContract.Friends.TABLE_NAME,null,testValues);
        assertEquals(1,friendRowId);
    }

}
