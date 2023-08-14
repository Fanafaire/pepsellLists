package com.example.pepsellchats.retrofit.chat.get;

public class Message {
    private long id;
    private MessageUser fromUser;
    private long messageTime;
    private String text;
    private String mediaURI;

    public Message(long id, MessageUser fromUser, long messageTime, String text, String mediaURI) {
        this.id = id;
        this.fromUser = fromUser;
        this.messageTime = messageTime;
        this.text = text;
        this.mediaURI = mediaURI;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MessageUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(MessageUser fromUser) {
        this.fromUser = fromUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMediaURI() {
        return mediaURI;
    }

    public void setMediaURI(String mediaURI) {
        this.mediaURI = mediaURI;
    }
}
