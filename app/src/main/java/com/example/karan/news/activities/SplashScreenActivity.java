package com.example.karan.news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.karan.news.firebase_essentials.FirebaseAuthentication;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchActivity();
    }

    /**
     * Check firebase Authentication for current user UID.
     * If it returns null, launch LoginActivity. Else launch HomeScreenActivity.
     */
    private void launchActivity() {

        // get current user UID
        FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(SplashScreenActivity.this);
        String currentUser = firebaseAuthentication.getCurrentUser();

        if (currentUser != null) {
            // launch HomeScreenActivity
            startActivity(new Intent(SplashScreenActivity.this, NewsHome.class));
        } else {
            // launch LoginActivity
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        }

        // remove SplashScreenActivity from stack
        finish();
    }
}
