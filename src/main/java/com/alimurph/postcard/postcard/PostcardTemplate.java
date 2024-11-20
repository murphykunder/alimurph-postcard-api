package com.alimurph.postcard.postcard;

public enum PostcardTemplate {

    BIRTHDAY_POSTCARD("birthday_postcard"),
    ANNIVERSARY_POSTCARD("anniversary_postcard");

    private String name;
    PostcardTemplate(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
