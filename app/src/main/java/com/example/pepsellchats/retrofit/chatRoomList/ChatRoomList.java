package com.example.pepsellchats.retrofit.chatRoomList;

import java.util.ArrayList;

public class ChatRoomList {
    private ArrayList<Chatroom> chatrooms;
    private String STATUS;
    private String ERROR;
    private String TYPE;
    private Long USER_ID;
    private Integer APP_ID;
    private Long DATE_TIME;

    public ArrayList<Chatroom> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(ArrayList<Chatroom> chatrooms) {
        this.chatrooms = chatrooms;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public Long getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(Long USER_ID) {
        this.USER_ID = USER_ID;
    }

    public Integer getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(Integer APP_ID) {
        this.APP_ID = APP_ID;
    }

    public Long getDATE_TIME() {
        return DATE_TIME;
    }

    public void setDATE_TIME(Long DATE_TIME) {
        this.DATE_TIME = DATE_TIME;
    }
}
