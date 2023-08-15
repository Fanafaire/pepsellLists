package com.example.pepsellchats.retrofit.chatList.post;

public class ChatListPOSTBody {
    private String TYPE;
    private String USER_ID;
    private String APP_ID;
    private long DATE_TIME;
    private ChatListChatPOSTBody CHAT;

    public ChatListPOSTBody(String TYPE, String USER_ID, String APP_ID, long DATE_TIME, ChatListChatPOSTBody CHAT) {
        this.TYPE = TYPE;
        this.USER_ID = USER_ID;
        this.APP_ID = APP_ID;
        this.DATE_TIME = DATE_TIME;
        this.CHAT = CHAT;
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

    public ChatListChatPOSTBody getCHAT() {
        return CHAT;
    }

    public void setCHAT(ChatListChatPOSTBody CHAT) {
        this.CHAT = CHAT;
    }
}
