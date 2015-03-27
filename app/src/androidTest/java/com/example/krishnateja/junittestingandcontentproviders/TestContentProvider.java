package com.example.krishnateja.junittestingandcontentproviders;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.krishnateja.junittestingandcontentproviders.data.FriendsDataBaseHelper;
import com.example.krishnateja.junittestingandcontentproviders.data.TablesContract;
import com.example.krishnateja.junittestingandcontentproviders.data.UserProvider;

/**
 * Created by krishnateja on 3/23/2015.
 */
public class TestContentProvider extends AndroidTestCase {
    public static final String LOG_TAG = TestContentProvider.class.getSimpleName();
    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                TablesContract.User.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                TablesContract.Friends.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                TablesContract.User.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Weather table during delete", 0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                TablesContract.Friends.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Location table during delete", 0, cursor.getCount());
        cursor.close();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecordsFromProvider();
    }

    /*
        This test checks to make sure that the content provider is registered correctly.
        Students: Uncomment this test to make sure you've correctly registered the WeatherProvider.
     */
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                UserProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: WeatherProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + TablesContract.CONTENT_AUTHORITY,
                    providerInfo.authority, TablesContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }
    /*
           This test doesn't touch the database.  It verifies that the ContentProvider returns
           the correct type for each type of URI that it can handle.
           Students: Uncomment this test to verify that your implementation of GetType is
           functioning correctly.
        */
    public void testGetType() {
        String type = mContext.getContentResolver().getType(TablesContract.User.CONTENT_URI);
        assertEquals("Error: ",
                TablesContract.User.CONTENT_TYPE, type);

        type = mContext.getContentResolver().getType(
               TablesContract.Friends.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error:",
                TablesContract.Friends.CONTENT_TYPE, type);


        type = mContext.getContentResolver().getType(
                TablesContract.Friends.buildUserFriendUri(TablesContract.PATH_USER));

        assertEquals("Error: ",
                TablesContract.Friends.CONTENT_TYPE, type);
    }

    /*
        This test uses the database directly to insert and then uses the ContentProvider to
        read out the data.  Uncomment this test to see if the basic weather query functionality
        given in the ContentProvider is working correctly.
     */
    public void testUserIntersion() {
        // insert our test records into the database
        // errors will be thrown here when you try to get a writable database.
        SQLiteDatabase db = new FriendsDataBaseHelper(this.mContext, FriendsDataBaseHelper.DATABASE_NAME, null, FriendsDataBaseHelper.DATABASE_VERSION).getWritableDatabase();
        // Second Step: Create ContentValues of what you want to insert
        ContentValues testValues =new ContentValues();
        testValues.put(TablesContract.User.COLUMN_USER_NAME,"krishna");

        long weatherRowId = db.insert(TablesContract.User.TABLE_NAME, null, testValues);
        assertTrue("Unable to Insert WeatherEntry into the Database", weatherRowId != -1);

        db.close();

        // Test the basic content provider query
        Cursor weatherCursor = mContext.getContentResolver().query(
                TablesContract.User.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

}
