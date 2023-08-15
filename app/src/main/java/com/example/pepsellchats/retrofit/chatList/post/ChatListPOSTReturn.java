package com.example.pepsellchats.retrofit.chatList.post;

public class ChatListPOSTReturn {
    private long CHAT_ID;
    private String STATUS;
    private String ERROR;
    private String TYPE;
    private String USER_ID;
    private String APP_ID;
    private long DATE_TIME;

    public ChatListPOSTReturn(long CHAT_ID, String STATUS, String ERROR,
                              String TYPE, String USER_ID, String APP_ID, long DATE_TIME) {
        this.CHAT_ID = CHAT_ID;
        this.STATUS = STATUS;
        this.ERROR = ERROR;
        this.TYPE = TYPE;
        this.USER_ID = USER_ID;
        this.APP_ID = APP_ID;
        this.DATE_TIME = DATE_TIME;
    }

    public long getCHAT_ID() {
        return CHAT_ID;
    }

    public void setCHAT_ID(long CHAT_ID) {
        this.CHAT_ID = CHAT_ID;
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

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public long getDATE_TIME() {
        return DATE_TIME;
    }

    public void setDATE_TIME(long DATE_TIME) {
        this.DATE_TIME = DATE_TIME;
    }
}
