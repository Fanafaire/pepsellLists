package com.example.pepsellchats.ui.chatRoomList;

public class ChatRoomListItem {
    String name, message, phone, url;
    long chatRoomId;

    public ChatRoomListItem(long chatRoomId, String name, String message, String phone, String url) {
        this.chatRoomId = chatRoomId;
        this.name = name;
        this.message = message;
        this.phone = phone;
        this.url = url;
    }

    public long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
