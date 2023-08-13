package com.example.pepsellchats.retrofit.chat;

public class ChatGETBody {
    private String TYPE;
    private String USER_ID;
    private String APP_ID;
    private long DATE_TIME;
    private long CHATROOM_ID;
    private long CHAT_ID;
    private long START_PERIOD;
    private long END_PERIOD;
    private int LIMIT;

    public ChatGETBody(String TYPE, String USER_ID, String APP_ID, long DATE_TIME,
                       long CHATROOM_ID, long CHAT_ID,
                       long START_PERIOD, long END_PERIOD, int LIMIT) {
        this.TYPE = TYPE;
        this.USER_ID = USER_ID;
        this.APP_ID = APP_ID;
        this.DATE_TIME = DATE_TIME;
        this.CHATROOM_ID = CHATROOM_ID;
        this.CHAT_ID = CHAT_ID;
        this.START_PERIOD = START_PERIOD;
        this.END_PERIOD = END_PERIOD;
        this.LIMIT = LIMIT;
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

    public long getCHATROOM_ID() {
        return CHATROOM_ID;
    }

    public void setCHATROOM_ID(long CHATROOM_ID) {
        this.CHATROOM_ID = CHATROOM_ID;
    }

    public long getCHAT_ID() {
        return CHAT_ID;
    }

    public void setCHAT_ID(long CHAT_ID) {
        this.CHAT_ID = CHAT_ID;
    }

    public long getSTART_PERIOD() {
        return START_PERIOD;
    }

    public void setSTART_PERIOD(long START_PERIOD) {
        this.START_PERIOD = START_PERIOD;
    }

    public long getEND_PERIOD() {
        return END_PERIOD;
    }

    public void setEND_PERIOD(long END_PERIOD) {
        this.END_PERIOD = END_PERIOD;
    }

    public int getLIMIT() {
        return LIMIT;
    }

    public void setLIMIT(int LIMIT) {
        this.LIMIT = LIMIT;
    }
}
