package com.example.krishnateja.junittestingandcontentproviders.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by krishnateja on 3/23/2015.
 */
public class UserProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FriendsDataBaseHelper mOpenHelper;

   public static final int USER = 100;
    public static final int FRIEND = 101;
    public static final int USER_FRIENDS = 102;
    private static final SQLiteQueryBuilder mFriendsByUser;

    static {
        mFriendsByUser = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        mFriendsByUser.setTables(
                TablesContract.User.TABLE_NAME + " INNER JOIN " +
                        TablesContract.Friends.TABLE_NAME +
                        " ON " + TablesContract.User.TABLE_NAME +
                        "." + TablesContract.User._ID +
                        " = " + TablesContract.Friends.TABLE_NAME +
                        "." + TablesContract.Friends.COLUMN_USER_KEY);
    }

    @Override
    public boolean onCreate() {

        mOpenHelper = new FriendsDataBaseHelper(getContext(),FriendsDataBaseHelper.DATABASE_NAME,null,FriendsDataBaseHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null;
        switch (sUriMatcher.match(uri)) {
            case USER: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TablesContract.User.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case FRIEND:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TablesContract.Friends.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            }
            case USER_FRIENDS:{
               retCursor= mFriendsByUser.query(
                        mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

            }
        }
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case USER:
                return TablesContract.User.CONTENT_TYPE;
            case FRIEND:
                return TablesContract.Friends.CONTENT_TYPE;
            case USER_FRIENDS:
                return TablesContract.Friends.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        switch (match) {
            case USER: {
                long _id = db.insert(TablesContract.User.TABLE_NAME, null, values);
                if (_id != -1) {
                    returnUri = TablesContract.User.buildUserUri(_id);
                }
                break;
            }
            case FRIEND: {
                long _id = db.insert(TablesContract.Friends.TABLE_NAME, null, values);
                if (_id != -1) {
                    returnUri = TablesContract.Friends.buildFriendUri(_id);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case USER:
                rowsDeleted = db.delete(
                        TablesContract.User.TABLE_NAME, selection, selectionArgs);
                break;
            case FRIEND:
                rowsDeleted = db.delete(
                        TablesContract.Friends.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            final int match = sUriMatcher.match(uri);
            int rowsUpdated;

            switch (match) {
                case USER:
                    rowsUpdated = db.update( TablesContract.User.TABLE_NAME, values, selection,
                            selectionArgs);
                    break;
                case FRIEND:
                    rowsUpdated = db.update( TablesContract.Friends.TABLE_NAME, values, selection,
                            selectionArgs);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
            if (rowsUpdated != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsUpdated;
    }

   public  static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TablesContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, TablesContract.PATH_USER, USER);
        matcher.addURI(authority, TablesContract.PATH_FRIEND, FRIEND);
        matcher.addURI(authority, TablesContract.PATH_FRIEND + "/*", USER_FRIENDS);
        return matcher;
    }
}
