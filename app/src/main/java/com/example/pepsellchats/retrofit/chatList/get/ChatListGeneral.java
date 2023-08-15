package com.example.pepsellchats.retrofit.chatList.get;

import java.util.ArrayList;

public class ChatListGeneral {
    private ArrayList<Chat> chatList;
    private String STATUS;
    private String ERROR;
    private String TYPE;
    private long USER_ID;
    private int APP_ID;
    private long DATE_TIME;

    public ChatListGeneral(ArrayList<Chat> chatList, String STATUS, String ERROR, String TYPE,
                           long USER_ID, int APP_ID, long DATE_TIME) {
        this.chatList = chatList;
        this.STATUS = STATUS;
        this.ERROR = ERROR;
        this.TYPE = TYPE;
        this.USER_ID = USER_ID;
        this.APP_ID = APP_ID;
        this.DATE_TIME = DATE_TIME;
    }

    public ArrayList<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(ArrayList<Chat> chatList) {
        this.chatList = chatList;
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

    public long getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(long USER_ID) {
        this.USER_ID = USER_ID;
    }

    public int getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(int APP_ID) {
        this.APP_ID = APP_ID;
    }

    public long getDATE_TIME() {
        return DATE_TIME;
    }

    public void setDATE_TIME(long DATE_TIME) {
        this.DATE_TIME = DATE_TIME;
    }
}
