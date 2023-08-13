package com.example.pepsellchats.ui.chatList;

public class ChatListItem {
    private long userId, chatId, chatRoomId;
    private String userName, userLogoURI, chatDescription, chatMediaURI, chatLastMesDate;

    public ChatListItem(long chatRoomId, long userId, long chatId, String userName, String userLogoURI,
                        String chatDescription, String chatMediaURI, String chatLastMesDate) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.chatId = chatId;
        this.userName = userName;
        this.userLogoURI = userLogoURI;
        this.chatDescription = chatDescription;
        this.chatMediaURI = chatMediaURI;
        this.chatLastMesDate = chatLastMesDate;
    }

    public long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogoURI() {
        return userLogoURI;
    }

    public void setUserLogoURI(String userLogoURI) {
        this.userLogoURI = userLogoURI;
    }

    public String getChatDescription() {
        return chatDescription;
    }

    public void setChatDescription(String chatDescription) {
        this.chatDescription = chatDescription;
    }

    public String getChatMediaURI() {
        return chatMediaURI;
    }

    public void setChatMediaURI(String chatMediaURI) {
        this.chatMediaURI = chatMediaURI;
    }

    public String getChatLastMesDate() {
        return chatLastMesDate;
    }

    public void setChatLastMesDate(String chatLastMesDate) {
        this.chatLastMesDate = chatLastMesDate;
    }
}
