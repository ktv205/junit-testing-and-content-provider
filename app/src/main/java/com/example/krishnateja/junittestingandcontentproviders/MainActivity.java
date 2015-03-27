package com.example.krishnateja.junittestingandcontentproviders;

import android.app.DialogFragment;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnateja.junittestingandcontentproviders.data.FriendsDataBaseHelper;
import com.example.krishnateja.junittestingandcontentproviders.data.TablesContract;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FriendsDataBaseHelper db=new FriendsDataBaseHelper(this,FriendsDataBaseHelper.DATABASE_NAME,null,FriendsDataBaseHelper.DATABASE_VERSION);
        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linear_layout);
        final EditText editText=(EditText)findViewById(R.id.username);
        Button button=(Button)findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text=new TextView(MainActivity.this);
                text.setText(editText.getText().toString());
                ContentValues values=new ContentValues();
                values.put(TablesContract.User.COLUMN_USER_NAME,editText.getText().toString());
                Uri uri = getContentResolver().insert(TablesContract.User.CONTENT_URI,values);
                final long id=ContentUris.parseId(uri);
                linearLayout.addView(text);
                setContentView(linearLayout);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FriendDialogFragment friend=new FriendDialogFragment();
                        friend.show(getFragmentManager(),"tag");
                        Bundle bundle=new Bundle();
                        bundle.putLong("id",id);
                        friend.setArguments(bundle);

                    }
                });

            }
        });
        Button showButton =(Button)findViewById(R.id.show);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ShowActivity.class));
            }
        });

    }

}
