package com.example.krishnateja.junittestingandcontentproviders;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.krishnateja.junittestingandcontentproviders.data.TablesContract;

/**
 * Created by krishnateja on 3/26/2015.
 */
public class ShowActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int URL_LOADER = 0;
    public String[] mFromColumns = {
            TablesContract.User.COLUMN_USER_NAME,TablesContract.Friends.COLUMN_FRIEND_NAME
    };
    public int[] mToFields = {
            android.R.id.text1,android.R.id.text2
    };
    ListView mListView;
    SimpleCursorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activty_show);
       Cursor cursor=getContentResolver().query(TablesContract.Friends.buildUserFriendUri(TablesContract.PATH_USER),null,null,null,null);
         cursor.moveToFirst();
        Toast.makeText(this,"cursor column length->"+cursor.getCount(),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"cursor user name->"+cursor.getString(cursor.getColumnIndex(TablesContract.User.COLUMN_USER_NAME)),Toast.LENGTH_LONG).show();
        getLoaderManager().initLoader(URL_LOADER, null, this);

// Gets a handle to a List View
        mListView  = (ListView) findViewById(R.id.listview);
/*
 * Defines a SimpleCursorAdapter for the ListView
 *
 */
        mAdapter=
                new SimpleCursorAdapter(
                        this,                // Current context
                        android.R.layout.simple_list_item_2,  // Layout for a single row
                        null,                // No Cursor yet
                        mFromColumns,        // Cursor columns to use
                        mToFields,           // Layout fields to use
                        0                    // No flags
                );
// Sets the adapter for the view
        mListView.setAdapter(mAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                TablesContract.Friends.buildUserFriendUri(TablesContract.PATH_USER),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }
}
