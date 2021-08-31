package com.bless.studexp.models;

public class Message {
    private String type;
    private  String file;
    private String fromId;
    private String messageText;
    private  String msgTime;
    private String video;
    private String image;
    private String imageCaption;

    public Message(String type, String file, String fromId, String messageText, String msgTime, String video, String image, String imageCaption) {
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

    public String getFile() {
        return file;
    }

    public String getFromId() {
        return fromId;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public String getVideo() {
        return video;
    }

    public String getImage() {
        return image;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", file='" + file + '\'' +
                ", fromId='" + fromId + '\'' +
                ", messageText='" + messageText + '\'' +
                ", msgTime='" + msgTime + '\'' +
                ", video='" + video + '\'' +
                ", image='" + image + '\'' +
                ", imageCaption='" + imageCaption + '\'' +
                '}';
    }
}
