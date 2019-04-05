package com.example.firebaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firebaseexample.adapter.ChatMessageAdapter;
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

    Toolbar toolbar;

    private DrawerLayout drawerLayout;

    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_myAccount:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyAccountFragment()).commit();
                        break;

                    case R.id.nav_setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                        break;
                    case R.id.nav_signout:
                        mAuth.signOut();
                        finish();
                        startActivity(getIntent());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_launcher_foreground,
         //       R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();



        editTxt = findViewById(R.id.message_input);
        ListView listView = findViewById(R.id.message_list);
        listView.setScrollingCacheEnabled(false);


        chatMessagesViewAdapter = new ChatMessageAdapter(getApplicationContext(), messages);
        listView.setAdapter(chatMessagesViewAdapter);

        database.getReference().addChildEventListener(getChatMessageListener());
    }


   /* @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){
            case R.id.nav_myAccount:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyAccountFragment()).commit();
                break;

            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).commit();
                break;
            case R.id.nav_signout:
                mAuth.signOut();
                finish();
                startActivity(getIntent());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }*/

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        DatabaseReference myRef = database.getReference(UUID.randomUUID().toString());
        myRef.setValue(new ChatMessage(message, currentUser.getDisplayName()));
        editTxt.getText().clear();
    }

    /*public void signOut(View view) {
        mAuth.signOut();
        finish();
        startActivity(getIntent());
    }*/

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
}
