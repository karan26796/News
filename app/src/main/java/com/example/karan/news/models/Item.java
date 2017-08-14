package com.example.karan.news.models;

import java.io.Serializable;

public class Item implements Serializable{

    private String detail;
    private String image;

    public Item() {
        // Default constructor required for calls to DataSnapshot.getValue(Item.class)
    }

    public Item(String detail, String image) {
        this.detail=detail;
        this.image=image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
