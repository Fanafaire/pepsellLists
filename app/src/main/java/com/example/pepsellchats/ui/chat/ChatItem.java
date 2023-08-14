package com.example.pepsellchats.ui.chat;

public class ChatItem {
    private long accUserId;
    private long userId;
    private String userName;
    private String userLogo;
    private String mImage;
    private String mText;
    private long time;

    public ChatItem(long accUserId, long userId, String userName, String userLogo,
                    String mImage, String mText, long time) {
        this.accUserId = accUserId;
        this.userId = userId;
        this.userName = userName;
        this.userLogo = userLogo;
        this.mImage = mImage;
        this.mText = mText;
        this.time = time;
    }

    public long getAccUserId() {
        return accUserId;
    }

    public void setAccUserId(long accUserId) {
        this.accUserId = accUserId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
