package com.example.krishnateja.junittestingandcontentproviders.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by krishnateja on 3/22/2015.
 */
public class TablesContract {
    public static final String SCHEME="content";
    public static final String CONTENT_AUTHORITY="com.example.krishnateja.junittestingandcontentproviders";
    public static final Uri BASE_CONTENT_URI= Uri.parse(SCHEME+"://"+CONTENT_AUTHORITY);

    public static final String PATH_USER="user";
    public static final String PATH_FRIEND="friend";
    public static final class User implements BaseColumns {

        public static final Uri CONTENT_URI=
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static Uri buildUserUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

         public static final String TABLE_NAME="user";
        public static final String COLUMN_USER_NAME="username";
    }

    public static final class Friends implements BaseColumns {
        public static final String TABLE_NAME="friend";
        public static final String COLUMN_FRIEND_NAME="friendname";
        public  static final String COLUMN_USER_KEY="user_id";

        public static final Uri CONTENT_URI=
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FRIEND).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FRIEND;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FRIEND;
        public static Uri buildFriendUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildUserFriendUri(String path_user){
            return CONTENT_URI.buildUpon().appendPath(path_user).build();
        }


    }
}
