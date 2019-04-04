package com.example.firebaseexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebaseexample.ChatGroupListActivity;
import com.example.firebaseexample.MainActivity;
import com.example.firebaseexample.R;
import com.example.firebaseexample.model.ChatGroup;

import java.util.ArrayList;
import java.util.List;

public class ChatGroupAdapter extends ArrayAdapter<ChatGroup> {
    private Context mContext;
    private final List<ChatGroup> groups;

    public ChatGroupAdapter(Context context, ArrayList<ChatGroup> list) {
        super(context, 0, list);
        mContext = context;
        this.groups = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.group, parent, false);

        ChatGroup group = groups.get(position);

        Button groupButton = listItem.findViewById(R.id.group);
        groupButton.setText(group.getName());
        groupButton.setTag(group.getGroupID());

        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupId = view.getTag().toString();
                String groupName = ((Button) view).getText().toString();

                Intent intent = new Intent(getContext(), MainActivity.class);

                intent.putExtra("group_id", groupId);
                intent.putExtra("group_name", groupName);
                getContext().startActivity(intent);
            }
        });


        return listItem;
    }


}
