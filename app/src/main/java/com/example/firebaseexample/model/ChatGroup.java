package com.example.firebaseexample.model;

import java.util.ArrayList;
import java.util.List;

public class ChatGroup {
    String groupID;
    String name;
    ArrayList<String> groupMembers;

    public ChatGroup(String groupID, String name, ArrayList<String> groupMembers ){
        this.groupID = groupID;
        this.groupMembers= groupMembers;
        this.name = name;

    }

    public ChatGroup(){

    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setGroupMembers(ArrayList<String> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<String> getGroupMembers() {
        return groupMembers;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
