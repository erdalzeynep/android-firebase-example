package com.example.firebaseexample;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.firebaseexample.adapter.ChatUserAdapter;
import com.example.firebaseexample.model.ChatGroup;
import com.example.firebaseexample.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatUserListActivity extends AppCompatActivity {
    private ChatUserAdapter chatUserAdapter;
    private ArrayList<User> users = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_user_list);

        database = FirebaseDatabase.getInstance();
        ListView listView = findViewById(R.id.list_user);
        listView.setScrollingCacheEnabled(false);


        chatUserAdapter = new ChatUserAdapter(getApplicationContext(), users);
        listView.setAdapter(chatUserAdapter);

        database.getReference("users").addChildEventListener(getChatUsersListener());

    }

    private ChildEventListener getChatUsersListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                User user = dataSnapshot.getValue(User.class);
                users.add(user);
                chatUserAdapter.notifyDataSetChanged();
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

}
