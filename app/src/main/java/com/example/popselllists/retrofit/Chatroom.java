package com.example.popselllists.retrofit;

public class Chatroom {
    private String name;
    private String hotlinePhone;
    private int ID;
    private String DESCRIPTION;
    private String MEDIA_URI;
    private int UNREAD_MESSAGE_COUNT;
    private String LAST_MESSAGE_DATE;

    public Chatroom(String name, String hotlinePhone, int ID, String DESCRIPTION, String MEDIA_URI, int UNREAD_MESSAGE_COUNT, String LAST_MESSAGE_DATE) {
        this.name = name;
        this.hotlinePhone = hotlinePhone;
        this.ID = ID;
        this.DESCRIPTION = DESCRIPTION;
        this.MEDIA_URI = MEDIA_URI;
        this.UNREAD_MESSAGE_COUNT = UNREAD_MESSAGE_COUNT;
        this.LAST_MESSAGE_DATE = LAST_MESSAGE_DATE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotlinePhone() {
        return hotlinePhone;
    }

    public void setHotlinePhone(String hotlinePhone) {
        this.hotlinePhone = hotlinePhone;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getMEDIA_URI() {
        return MEDIA_URI;
    }

    public void setMEDIA_URI(String MEDIA_URI) {
        this.MEDIA_URI = MEDIA_URI;
    }

    public int getUNREAD_MESSAGE_COUNT() {
        return UNREAD_MESSAGE_COUNT;
    }

    public void setUNREAD_MESSAGE_COUNT(int UNREAD_MESSAGE_COUNT) {
        this.UNREAD_MESSAGE_COUNT = UNREAD_MESSAGE_COUNT;
    }

    public String getLAST_MESSAGE_DATE() {
        return LAST_MESSAGE_DATE;
    }

    public void setLAST_MESSAGE_DATE(String LAST_MESSAGE_DATE) {
        this.LAST_MESSAGE_DATE = LAST_MESSAGE_DATE;
    }
}
