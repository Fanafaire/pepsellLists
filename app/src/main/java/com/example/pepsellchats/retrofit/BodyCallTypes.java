package com.example.pepsellchats.retrofit;

public enum BodyCallTypes {
    CHATROOM_LIST,
    CHAT_LIST,
    CHAT_HISTORY,
    CREATE_TEXT_CHAT,
    SEND_CHAT_MEDIA,
    SEND_CHAT_TEXT,
    CREATE_MEDIA_CHAT;

    @Override
    public String toString() {
        return name();
    }
}
