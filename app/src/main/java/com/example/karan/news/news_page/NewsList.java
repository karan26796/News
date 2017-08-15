package com.example.karan.news.news_page;

/**
 * Created by karan on 7/3/2017.
 */

public class NewsList {
    public NewsList() {
    }

    private String title;
    private String description;
    private String image;
    private String date;

    
    //Defined for predefined cities
    public NewsList(String title, String image, String date,String description,String detail)
    {
        this.title=title;
        this.image=image;
        this.date=date;
        this.description=description;
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
