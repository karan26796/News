package com.example.karan.news.utils;

import android.app.Activity;
import android.content.Intent;
import com.example.karan.news.activities.AppPreferences;
import com.example.karan.news.activities.LoginActivity;
import com.example.karan.news.activities.NewsDetails;
import com.example.karan.news.activities.NewsHome;
import com.example.karan.news.activities.SignUp;
import com.example.karan.news.models.Item;

import java.util.ArrayList;

/**
 * Handle launching different activities in launch scenario
 * All the intents for activity launch are defined here to clean corresponding
 * activity code.
 */

public class LaunchManager {

    //Home activity is launched via intent wherever this method is called
    public static void launchHome(Activity activity) {
        Intent intent = new Intent(activity, NewsHome.class);
        activity.startActivity(intent);
    }

    //Home activity is launched with the news category selected by the user previously
    public static void categoryFragment(Activity activity,String category) {
        Intent intent = new Intent(activity, NewsHome.class);
        intent.putExtra(Constants.CATEGORY_NAME,category);
        activity.startActivity(intent);
    }

    //Settings activity is launched via intent wherever this method is called
    public static void launchSettings(Activity activity) {
        Intent intent = new Intent(activity, AppPreferences.class);
        activity.startActivity(intent);
    }

    //User sign up screen is launched via intent wherever this method is called
    public static void showSignUpScreen(Activity activity) {
        Intent intent = new Intent(activity, SignUp.class);
        activity.startActivity(intent);
        activity.finish();
    }

    //User sign in Screen is launched via intent wherever this method is called
    public static void showSignInScreen(Activity activity) {
        Intent intent = new Intent(activity,LoginActivity.class);
        //clear activity stack while launching sign in screen
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    /**Valuable resources like position,color of toolbar and news category are sent to the
    Details activity using this method via Intent and later fetched in the details activity*/

    //News details page is opened from click of news article on News list
    public static void showDetailsPage(Activity activity, int position, String category , int color, Item newsItem){
        Intent intent = new Intent(activity,NewsDetails.class);
        intent.putExtra(Constants.POSITION,position);
        intent.putExtra(Constants.CATEGORY_NAME,category);
        intent.putExtra(Constants.COLOR_VALUE,color);
        intent.putExtra(Constants.NEWS_DETAILS, newsItem);
        activity.startActivity(intent);
        activity.finish();
    }
}
