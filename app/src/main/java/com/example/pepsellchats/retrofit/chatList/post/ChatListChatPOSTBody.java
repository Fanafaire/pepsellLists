package com.example.pepsellchats.retrofit.chatList.post;

public class ChatListChatPOSTBody {
    private long ID;
    private long CHATROOM_ID;
    private String DESCRIPTION;

    public ChatListChatPOSTBody(long ID, long CHATROOM_ID, String DESCRIPTION) {
        this.ID = ID;
        this.CHATROOM_ID = CHATROOM_ID;
        this.DESCRIPTION = DESCRIPTION;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getCHATROOM_ID() {
        return CHATROOM_ID;
    }

    public void setCHATROOM_ID(long CHATROOM_ID) {
        this.CHATROOM_ID = CHATROOM_ID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
}
