package com.example.firebaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.firebaseexample.adapter.ChatMessageAdapter;
import com.example.firebaseexample.model.ChatMessage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser currentUser;

    private EditText editTxt;
    private ChatMessageAdapter chatMessagesViewAdapter;
    private ArrayList<ChatMessage> messages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        setContentView(R.layout.activity_main);

        editTxt = findViewById(R.id.message_input);
        ListView listView = findViewById(R.id.message_list);
        listView.setScrollingCacheEnabled(false);


        chatMessagesViewAdapter = new ChatMessageAdapter(getApplicationContext(), messages);
        listView.setAdapter(chatMessagesViewAdapter);

        database.getReference("messages").addChildEventListener(getChatMessageListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.currentUser = mAuth.getCurrentUser();
        if (null == currentUser) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void sendMessage(View view) {
        String message = ((EditText) findViewById(R.id.message_input)).getText().toString();
        DatabaseReference myRef = database.getReference("messages").child(UUID.randomUUID().toString());
        myRef.setValue(new ChatMessage(message, currentUser.getDisplayName()));
        editTxt.getText().clear();
    }

    public void signOut(View view) {
        mAuth.signOut();
        finish();
        startActivity(getIntent());
    }

    private ChildEventListener getChatMessageListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                messages.add(message);
                chatMessagesViewAdapter.notifyDataSetChanged();
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

    public void goToChat(View view) {
        startActivity(new Intent(this, ChatUserListActivity.class));

    }
}
