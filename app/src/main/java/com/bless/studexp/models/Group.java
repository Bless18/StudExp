package com.bless.studexp.models;

import java.util.ArrayList;

public class Group {
    private String id;
    private  String adminId;
    private long createdAt;
    private String groupIcon;
    private ArrayList<String> members;
    private  String name;
    private String recentMessage;

    public Group(String id, String adminId, long createdAt, String groupIcon, ArrayList<String> members, String name, String recentMessage) {
        this.id = id;
        this.adminId = adminId;
        this.createdAt = createdAt;
        this.groupIcon = groupIcon;
        this.members = members;
        this.name = name;
        this.recentMessage = recentMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
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

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", adminId='" + adminId + '\'' +
                ", createdAt=" + createdAt +
                ", groupIcon='" + groupIcon + '\'' +
                ", members=" + members +
                ", name='" + name + '\'' +
                ", recentMessage='" + recentMessage + '\'' +
                '}';
    }
}
