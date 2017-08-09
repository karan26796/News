package com.example.karan.news.news_page;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by karan on 7/3/2017.
 */

public class NewsList {
    public NewsList() {
    }

    private String title,description,image,date;
    
    //Defined for predefined cities
    public NewsList(String title, String image,String date)
    {
        this.title=title;
        this.image=image;
        this.date=date;
        
    }
    //Defined for user entered title

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

}
