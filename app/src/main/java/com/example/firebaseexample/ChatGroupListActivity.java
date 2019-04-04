package com.example.firebaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.firebaseexample.adapter.ChatGroupAdapter;
import com.example.firebaseexample.model.ChatGroup;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class ChatGroupListActivity extends AppCompatActivity {
    private ChatGroupAdapter chatGroupAdapter;
    private ArrayList<ChatGroup> groups = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_group_list);

        database = FirebaseDatabase.getInstance();
        ListView listView = findViewById(R.id.list_group);
        listView.setScrollingCacheEnabled(false);

        chatGroupAdapter = new ChatGroupAdapter(getApplicationContext(), groups);
        listView.setAdapter(chatGroupAdapter);
        database.getReference("chat_groups").addChildEventListener(getChatGroupListener());

    }

    private ChildEventListener getChatGroupListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                ChatGroup group = dataSnapshot.getValue(ChatGroup.class);
                groups.add(group);
                chatGroupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
    }

    public void createNewChatGroup(View view) {

        setContentView(R.layout.create_group);
    }

    public void launchGroupChat(View view) {

        EditText groupNameText = findViewById(R.id.group_name);
        setContentView(R.layout.create_group);
        String groupID = UUID.randomUUID().toString();
        String groupName = groupNameText.getText().toString();
        ArrayList<String> groupMembers = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("/chat_groups").child(groupID);
        reference.setValue(new ChatGroup(groupID, groupName, groupMembers));

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("group_id", groupID);
        intent.putExtra("group_name", groupName);
        startActivity(intent);
    }

}
