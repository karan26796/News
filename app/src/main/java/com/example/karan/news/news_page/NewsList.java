package com.example.karan.news.news_page;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by karan on 7/3/2017.
 */

public class NewsList {
    public NewsList() {
    }

    private String title,description,image;
    
    //Defined for predefined cities
    public NewsList(String title, String description, String image)
    {
        this.title=title;
        this.description=description;
        this.image=image;
        
    }
    //Defined for user entered title
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
   
}
