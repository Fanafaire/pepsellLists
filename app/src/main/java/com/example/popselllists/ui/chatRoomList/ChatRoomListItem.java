package com.example.popselllists.ui.chatRoomList;

public class ChatRoomListItem {
    String name, message, phone, url;
    int chatId;

    public ChatRoomListItem(int chatId, String name, String message, String phone, String url) {
        this.chatId = chatId;
        this.name = name;
        this.message = message;
        this.phone = phone;
        this.url = url;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
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
