package com.bless.studexp.models;

import java.util.ArrayList;

public class User {
    private String name;
    private String level;
    private String email;
    private ArrayList<String> groupId;
    private int lActiveness;
    private String town;
    private String imageUrl;
    private String key;
    public User() {
    }

    public User(String name, String level, String email, ArrayList<String> groupId, int lActiveness, String town, String imageUrl) {
        this.name = name;
        this.level = level;
        this.email = email;
        this.groupId = groupId;
        this.lActiveness = lActiveness;
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

    public int getlActiveness() {
        return lActiveness;
    }

    public void setlActiveness(int lActiveness) {
        this.lActiveness = lActiveness;
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
                ", lActiveness=" + lActiveness +
                ", town='" + town + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ",key='"+ key + '\''+
                '}';
    }
}