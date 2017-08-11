package com.example.karan.news.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.karan.news.R;
import com.example.karan.news.activities.LoginActivity;
import com.example.karan.news.activities.NewsDetails;
import com.example.karan.news.activities.NewsHome;
import com.example.karan.news.activities.SignUp;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;


/**
 * Handle launching different activities in launch scenario
 *
 */

public class LaunchManager {

    public static void launchHome(Activity activity) {
        Intent intent = new Intent(activity, NewsHome.class);
        activity.startActivity(intent);
    }

    public static void showSignUpScreen(Activity activity) {
        Intent intent = new Intent(activity, SignUp.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void showSignInScreen(Activity activity) {
        Intent intent = new Intent(activity,LoginActivity.class);
        //clear activity stack while launching signin screen
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void showDetailsPage(Activity activity,int position,String category){
        Intent intent = new Intent(activity,NewsDetails.class);
        intent.putExtra("position",position);
        intent.putExtra("category",category);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void showDialog(final Activity activity) {
        AlertDialog alertbox = new AlertDialog.Builder(activity)
                .setMessage(R.string.logout_out)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuthentication firebaseAuthentication=new FirebaseAuthentication(activity);
                        firebaseAuthentication.logoutUser();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        return;
                    }
                })
                .show();
    }


}
