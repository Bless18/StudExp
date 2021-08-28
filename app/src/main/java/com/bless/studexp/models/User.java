package com.bless.studexp.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String level;
    private String email;
    private ArrayList<String> groupId;
    private int numQuesAns;
    private String town;
    private String imageUrl;
    private String key;
    public User() {
    }

    public User(String name, String level, String email, ArrayList<String> groupId, int numQuesAns, String town, String imageUrl) {
        this.name = name;
        this.level = level;
        this.email = email;
        this.groupId = groupId;
        this.numQuesAns = numQuesAns;
        this.town = town;
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getGroupId() {
        return groupId;
    }

    public void setGroupId(ArrayList<String> groupId) {
        this.groupId = groupId;
    }

    public int getNumQuesAns() {
        return numQuesAns;
    }

    public void setNumQuesAns(int numQuesAns) {
        this.numQuesAns = numQuesAns;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", email='" + email + '\'' +
                ", groupId=" + groupId +
                ", numQuesAns=" + numQuesAns +
                ", town='" + town + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ",key='"+ key + '\''+
                '}';
    }
}