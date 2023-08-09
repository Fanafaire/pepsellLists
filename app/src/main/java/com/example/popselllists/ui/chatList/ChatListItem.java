package com.example.popselllists.ui.chatList;

import android.media.Image;

public class ChatListItem {
    String name, message, phone;
//    Image image;

    public ChatListItem(String name, String message, String phone, Image image) {
        this.name = name;
        this.message = message;
        this.phone = phone;
//        this.image = image;
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

//    public Image getImage() {
//        return image;
//    }
//
//    public void setImage(Image image) {
//        this.image = image;
//    }
}
