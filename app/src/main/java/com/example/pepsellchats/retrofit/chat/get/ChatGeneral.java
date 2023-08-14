package com.example.pepsellchats.retrofit.chat.get;

import java.util.ArrayList;

public class ChatGeneral {
    private long CHAT_ID;
    private ArrayList<Message> MESSAGES;
    private String TYPE;
    private long USER_ID;
    private int APP_ID;
    private long DATA_TIME;

    public ChatGeneral(long CHAT_ID, ArrayList<Message> MESSAGES, String TYPE, long USER_ID, int APP_ID, long DATA_TIME) {
        this.CHAT_ID = CHAT_ID;
        this.MESSAGES = MESSAGES;
        this.TYPE = TYPE;
        this.USER_ID = USER_ID;
        this.APP_ID = APP_ID;
        this.DATA_TIME = DATA_TIME;
    }

    public long getCHAT_ID() {
        return CHAT_ID;
    }

    public void setCHAT_ID(long CHAT_ID) {
        this.CHAT_ID = CHAT_ID;
    }

    public ArrayList<Message> getMESSAGES() {
        return MESSAGES;
    }

    public void setMESSAGES(ArrayList<Message> MESSAGES) {
        this.MESSAGES = MESSAGES;
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

    public long getDATA_TIME() {
        return DATA_TIME;
    }

    public void setDATA_TIME(long DATA_TIME) {
        this.DATA_TIME = DATA_TIME;
    }
}
