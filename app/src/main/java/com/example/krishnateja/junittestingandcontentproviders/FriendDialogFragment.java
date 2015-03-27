package com.example.krishnateja.junittestingandcontentproviders;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krishnateja.junittestingandcontentproviders.data.TablesContract;

/**
 * Created by krishnateja on 3/26/2015.
 */
public class FriendDialogFragment extends DialogFragment {
    View view;
    EditText editText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dialog_friend,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button okButton=(Button)view.findViewById(R.id.ok);
        editText=(EditText)view.findViewById(R.id.friendname);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name=editText.getText().toString();
                long id=getArguments().getLong("id");
                //Toast.makeText(getActivity(),"id->"+id,Toast.LENGTH_SHORT).show();
                ContentValues values=new ContentValues();
                values.put(TablesContract.Friends.COLUMN_FRIEND_NAME,name);
                values.put(TablesContract.Friends.COLUMN_USER_KEY,id);
                Uri uri=getActivity().getContentResolver().insert(TablesContract.Friends.CONTENT_URI,values);
                Toast.makeText(getActivity(),"uri->"+uri.toString(),Toast.LENGTH_SHORT).show();
                getDialog().dismiss();

            }
        });

    }
}
