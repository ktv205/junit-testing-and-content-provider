package com.example.krishnateja.junittestingandcontentproviders;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.krishnateja.junittestingandcontentproviders.data.TablesContract;
import com.example.krishnateja.junittestingandcontentproviders.data.UserProvider;

/**
 * Created by krishnateja on 3/23/2015.
 */
public class TestUriMatcher extends AndroidTestCase {
    private static final String USER_PATH="user";
    private static final Uri TEST_USER_DIR= TablesContract.User.CONTENT_URI;
    private static final Uri TEST_FRIENDS_DIR=TablesContract.Friends.CONTENT_URI;
    private  static final Uri TEST_USER_FRIENDS_DIR=TablesContract.Friends.buildUserFriendUri(USER_PATH);

    public void testUriMatcher(){
        UriMatcher testMatcher= UserProvider.buildUriMatcher();
        assertEquals("Error: The WEATHER URI was matched incorrectly.",
                testMatcher.match(TEST_USER_DIR), UserProvider.USER);
        assertEquals("Error: The WEATHER URI was matched incorrectly.",
                testMatcher.match(TEST_FRIENDS_DIR), UserProvider.FRIEND);
        assertEquals("Error: The WEATHER URI was matched incorrectly.",
                testMatcher.match(TEST_USER_FRIENDS_DIR), UserProvider.USER_FRIENDS);

    }
}
