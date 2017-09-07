package com.example.karan.news.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import com.example.karan.news.R;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;


public class SplashScreenActivity extends AppCompatActivity {

    private Window window;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash_screen);
        super.onCreate(savedInstanceState);

        window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.off_white));
        launchActivity();
    }

    /**
     * Checks firebase Authentication for current user UID.
     *
     * If the user is found to be null, the app opens to the login screen where the user can login
     * or create a new account in case of a new user.
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
