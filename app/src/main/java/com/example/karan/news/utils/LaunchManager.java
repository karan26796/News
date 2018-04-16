package com.example.karan.news.utils;

import android.app.Activity;
import android.content.Intent;
import com.example.karan.news.activities.SettingsActivity;
import com.example.karan.news.activities.LoginActivity;
import com.example.karan.news.activities.NewsDetailsActivity;
import com.example.karan.news.activities.NewsHomeActivity;
import com.example.karan.news.activities.SignUpActivity;
import com.example.karan.news.models.Item;

/**
 * Handle launching different activities in launch scenario
 * All the intents for activity launch are defined here to clean corresponding
 * activity code.
 */

public class LaunchManager {

    //Home activity is launched via intent wherever this method is called
    public static void launchHome(Activity activity) {
        Intent intent = new Intent(activity, NewsHomeActivity.class);
        activity.startActivity(intent);
    }

    //Home activity is launched with the news category selected by the user previously
    public static void categoryFragment(Activity activity,String category,boolean status,int position) {
        Intent intent = new Intent(activity, NewsHomeActivity.class);
        intent.putExtra(Constants.CATEGORY_NAME,category);
        intent.putExtra(Constants.POSITION,position);
        intent.putExtra(Constants.READ_ARTICLES_STATUS_SHARED_PREFERENCES,status);
        activity.startActivity(intent);
    }

    //Settings activity is launched via intent wherever this method is called
    public static void launchSettings(Activity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    //User sign up screen is launched via intent wherever this method is called
    public static void showSignUpScreen(Activity activity) {
        Intent intent = new Intent(activity, SignUpActivity.class);
        activity.startActivity(intent);
    }

    //User sign in Screen is launched via intent wherever this method is called
    public static void showSignInScreen(Activity activity) {
        Intent intent = new Intent(activity,LoginActivity.class);
        //clear activity stack while launching sign in screen
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    /**Valuable resources like position,color of toolbar and news category are sent to the
    Details activity using this method via Intent and later fetched in the details activity*/

    //News details page is opened from click of news article on News list
    //Exit app method used to exit app from homepage
    public static void exitApp(Activity activity){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}
