package com.example.pepsellchats.ui.chatList;

public class ChatListItem {
    private int userId, chatId;
    private String userName, userLogoURI, chatDescription, chatMediaURI, chatLastMesDate;

    public ChatListItem(int userId, int chatId, String userName, String userLogoURI,
                        String chatDescription, String chatMediaURI, String chatLastMesDate) {
        this.userId = userId;
        this.chatId = chatId;
        this.userName = userName;
        this.userLogoURI = userLogoURI;
        this.chatDescription = chatDescription;
        this.chatMediaURI = chatMediaURI;
        this.chatLastMesDate = chatLastMesDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
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
