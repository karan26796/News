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
    Window window;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash_screen);
        super.onCreate(savedInstanceState);

        window=getWindow();
        window.setStatusBarColor(getColor(R.color.button_gradient_color_1));
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
