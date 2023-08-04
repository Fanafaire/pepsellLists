package com.example.popselllists.ui.home;

public class CardsItem {
    String bigText, smallText;
    Boolean textType;

    public CardsItem(String bigText, String smallText, Boolean textType) {
        this.bigText = bigText;
        this.smallText = smallText;
        this.textType = textType;
    }

    public String getBigText() {
        return bigText;
    }

    public void setBigText(String text) {
        this.bigText = text;
    }

    public String getSmallText() {
        return smallText;
    }

    public void setSmallText(String smallText) {
        this.smallText = smallText;
    }

    public Boolean getTextType() {
        return textType;
    }

    public void setTextType(Boolean textType) {
        this.textType = textType;
    }
}
