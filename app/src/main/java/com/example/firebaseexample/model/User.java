package com.example.firebaseexample.model;

public class User {
    private String displayName;
    private String email;
    private String uid;

    public User() {
    }

    public User(String displayName, String email, String uid){
        this.displayName = displayName;
        this.email = email;
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

