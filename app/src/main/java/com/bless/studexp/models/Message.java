package com.bless.studexp.models;

public class Message {
    private String type;
    private  String file;
    private String fromId;
    private String messageText;
    private  long msgTime;
    private String video;
    private String image;
    private String imageCaption;

    public Message(String type, String file, String fromId, String messageText, long msgTime, String video, String image, String imageCaption) {
        this.type = type;
        this.file = file;
        this.fromId = fromId;
        this.messageText = messageText;
        this.msgTime = msgTime;
        this.video = video;
        this.image = image;
        this.imageCaption = imageCaption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
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

    public String getImageCaption() {
        return imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", file='" + file + '\'' +
                ", fromId='" + fromId + '\'' +
                ", messageText='" + messageText + '\'' +
                ", msgTime=" + msgTime +
                ", video='" + video + '\'' +
                ", image='" + image + '\'' +
                ", imageCaption='" + imageCaption + '\'' +
                '}';
    }
}
