package com.example.pepsellchats.retrofit.chatList;

public class Chat {
    private int ID;
    private int chatroomID;
    private ChatUser user;
    private String DESCRIPTION;
    private String MEDIA_URI;
    private int UNREAD_MESSAGE_DATE;
    private String LAST_MESSAGE_DATE;

    public Chat(int ID, int chatroomID, ChatUser user,
                String DESCRIPTION, String MEDIA_URI,
                int UNREAD_MESSAGE_DATE, String LAST_MESSAGE_DATE) {
        this.ID = ID;
        this.chatroomID = chatroomID;
        this.user = user;
        this.DESCRIPTION = DESCRIPTION;
        this.MEDIA_URI = MEDIA_URI;
        this.UNREAD_MESSAGE_DATE = UNREAD_MESSAGE_DATE;
        this.LAST_MESSAGE_DATE = LAST_MESSAGE_DATE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getChatroomID() {
        return chatroomID;
    }

    public void setChatroomID(int chatroomID) {
        this.chatroomID = chatroomID;
    }

    public ChatUser getUser() {
        return user;
    }

    public void setUser(ChatUser user) {
        this.user = user;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getMEDIA_URI() {
        return MEDIA_URI;
    }

    public void setMEDIA_URI(String MEDIA_URI) {
        this.MEDIA_URI = MEDIA_URI;
    }

    public int getUNREAD_MESSAGE_DATE() {
        return UNREAD_MESSAGE_DATE;
    }

    public void setUNREAD_MESSAGE_DATE(int UNREAD_MESSAGE_DATE) {
        this.UNREAD_MESSAGE_DATE = UNREAD_MESSAGE_DATE;
    }

    public String getLAST_MESSAGE_DATE() {
        return LAST_MESSAGE_DATE;
    }

    public void setLAST_MESSAGE_DATE(String LAST_MESSAGE_DATE) {
        this.LAST_MESSAGE_DATE = LAST_MESSAGE_DATE;
    }
}
