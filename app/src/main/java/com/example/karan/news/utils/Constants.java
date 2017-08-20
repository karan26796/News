package com.example.karan.news.utils;

/**
 * Created by karan on 7/13/2017.
 *
 * Defined here are some frequently user String values
 * that are used as keys and otherwise to minimize usage
 * of hardcoded strings every time and providing uniformity
 * and legibility to the overall code.
 */

public class Constants {

    public static final String CONNECTIVITY_CHANGE_ACTION ="android.net.conn.CONNECTIVITY_CHANGE" ;

/*---------------------------------------------------------------------------------------------------------------------------------*/
//Mentioned below are some of the common parameters that are required to be sent at different times in different activities
/*---------------------------------------------------------------------------------------------------------------------------------*/
    public static final String CATEGORY_NAME="category";
    public static final String POSITION ="position" ;
    public static final String COLOR_VALUE ="color" ;
    public static final String TOOLBAR_COLOR = "toolbar_color";
    public static final String USERNAME = "username";

/*------------------------------------------------------------------------------------------------------------------*/
//Mentioned below are the news categories that'll be displayed in Navigation drawer
/*------------------------------------------------------------------------------------------------------------------*/
    public static final String SPORTS_NEWS_CATEGORY ="Sports";
    public static final String POLITICS_NEWS_CATEGORY ="Politics";
    public static final String WORLD_NEWS_CATEGORY ="World";
    public static final String TOP_STORIES_NEWS_CATEGORY ="Top Stories";
    public static final String BOOKMARK_CATEGORY ="Bookmark";

/*------------------------------------------------------------------------------------------------------------------*/
//Mentioned below are some custom constant fields used across the code.
/*------------------------------------------------------------------------------------------------------------------*/
    public static final String PREFERENCES ="my_preferences" ;
    public static final String USERS_KEY ="Users";
    public static final String NEWS_DETAILS ="news_details" ;
    public static final String READ_ARTICLES_STATUS_SHARED_PREFERENCES = "MyPrefs";
    public static final String KEY_APP_THEME = "app_theme";
    public static final String KEY_TEXT_SIZE ="text_size" ;
    public static final String KEY_DOWNLOAD_IMAGES ="image_download" ;
}
