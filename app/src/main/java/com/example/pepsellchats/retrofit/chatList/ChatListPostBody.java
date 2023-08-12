package com.example.pepsellchats.retrofit.chatList;

public class ChatListPostBody {
    private String TYPE;
    private String USER_ID;
    private String APP_ID;
    private long DATE_TIME;
    private int CHATROOM_ID;
    private long START_PERIOD;
    private long FINISH_PERIOD;
    private long LIMIT;

    public ChatListPostBody(String TYPE, String USER_ID, String APP_ID, long DATE_TIME,
                            int CHATROOM_ID, long START_PERIOD, long FINISH_PERIOD, long LIMIT) {
        this.TYPE = TYPE;
        this.USER_ID = USER_ID;
        this.APP_ID = APP_ID;
        this.DATE_TIME = DATE_TIME;
        this.CHATROOM_ID = CHATROOM_ID;
        this.START_PERIOD = START_PERIOD;
        this.FINISH_PERIOD = FINISH_PERIOD;
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

    public int getCHATROOM_ID() {
        return CHATROOM_ID;
    }

    public void setCHATROOM_ID(int CHATROOM_ID) {
        this.CHATROOM_ID = CHATROOM_ID;
    }

    public long getSTART_PERIOD() {
        return START_PERIOD;
    }

    public void setSTART_PERIOD(long START_PERIOD) {
        this.START_PERIOD = START_PERIOD;
    }

    public long getFINISH_PERIOD() {
        return FINISH_PERIOD;
    }

    public void setFINISH_PERIOD(long FINISH_PERIOD) {
        this.FINISH_PERIOD = FINISH_PERIOD;
    }

    public long getLIMIT() {
        return LIMIT;
    }

    public void setLIMIT(long LIMIT) {
        this.LIMIT = LIMIT;
    }
}
