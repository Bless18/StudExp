package com.bless.studexp.models;

public class Message {
    private String chatType;
    private String contextType;
    private  String file;
    private String fromId;
    private String message;
    private  String msgTime;
    private String toId;
    private String video;
    private String image;

    public Message(String chatType, String contextType, String file, String fromId, String message, String msgTime, String toId, String video, String image) {
        this.chatType = chatType;
        this.contextType = contextType;
        this.file = file;
        this.fromId = fromId;
        this.message = message;
        this.msgTime = msgTime;
        this.toId = toId;
        this.video = video;
        this.image = image;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Message{" +
                "chatType='" + chatType + '\'' +
                ", contextType='" + contextType + '\'' +
                ", file='" + file + '\'' +
                ", fromId='" + fromId + '\'' +
                ", message='" + message + '\'' +
                ", msgTime='" + msgTime + '\'' +
                ", toId='" + toId + '\'' +
                ", video='" + video + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
