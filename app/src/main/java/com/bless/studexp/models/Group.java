package com.bless.studexp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    private  String id;
    private String groupIcon;
    private ArrayList<String> members;
    private  String name;
    private String recentMessage;
    private String description;
    private String category;

    public Group() {
    }

    public Group(String id, String groupIcon, ArrayList<String> members, String name, String recentMessage, String description, String category) {
        this.id =id;
        this.groupIcon = groupIcon;
        this.members = members;
        this.name = name;
        this.recentMessage = recentMessage;
        this.description = description;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setAdminId(String adminId) {
        this.id = adminId;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecentMessage() {
        return recentMessage;
    }

    public void setRecentMessage(String recentMessage) {
        this.recentMessage = recentMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", groupIcon='" + groupIcon + '\'' +
                ", members=" + members +
                ", name='" + name + '\'' +
                ", recentMessage='" + recentMessage + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
