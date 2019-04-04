package com.example.firebaseexample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.firebaseexample.R;
import com.example.firebaseexample.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatUserAdapter  extends ArrayAdapter<User> {
    private Context mContext;
    private final List<User> users;

    public ChatUserAdapter(Context context, ArrayList<User> list) {
        super(context ,0, list );
        mContext = context;
        users = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.user, parent, false);

       User user = users.get(position);

        Button userText = listItem.findViewById(R.id.user);
        userText.setText(user.getDisplayName());
        userText.setTag(user.getUid());

        return listItem;
    }
}
