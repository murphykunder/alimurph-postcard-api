package com.alimurph.postcard.postcard;

public enum OccasionType {
    ANNIVERSARY("anniversary"),
    BIRTHDAY("birthday");

    private String name;

    OccasionType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
