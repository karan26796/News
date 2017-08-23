package com.example.karan.news.models;

import java.io.Serializable;

public class Item implements Serializable{

    private String detail,image,title,date,description;

    public Item(){

    }

    public Item(String detail, String image,String title,String date,String description) {
        this.detail=detail;
        this.image=image;
        this.title=title;
        this.date=date;
        this.description=description;
    }


    public String getDetail() {
        return detail;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDescription() {
        return description;
    }
}
