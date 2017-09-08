package com.example.karan.news.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.karan.news.R;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;

/**
 * Created by karan on 7/8/2017.
 *
 * User login activity takes existing user's details
 * and checks if they are already registered with the app.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText pass,email;
    private Button login,user;
    private Window window;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        user=(Button) findViewById(R.id.sign_up);

        //window of the app screen is accessed to change color of status bar
        window=getWindow();

        pass=(EditText) findViewById(R.id.pass);
        email=(EditText)findViewById(R.id.email);

        login=(Button) findViewById(R.id.sign_in);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.gray));
        login.setOnClickListener(this);
        user.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    /*Method checks whether the entered details match that of an existing user
    and logs in if the details match.*/
    private void loginUser() {

        // setup progress dialog
        ProgressDialog mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setTitle(getString(R.string.start_progress_dialog_title));
        mProgressDialog.setMessage(getString(R.string.start_progress_dialog_message));
        mProgressDialog.setCanceledOnTouchOutside(false);

        String mEmail = email.getText().toString().trim();
        String mPass = pass.getText().toString().trim();

        if (!(TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPass))) {

            // show progress dialog
            mProgressDialog.show();
            // login registered user
            FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(this);
            firebaseAuthentication.loginUser(mEmail, mPass, mProgressDialog);

            SharedPreferences sharedPreferences=getSharedPreferences(Constants.PREFERENCES,MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            sharedPreferences.getString(Constants.USERNAME,mEmail);
            edit.apply();

        } else {
            Toast.makeText(LoginActivity.this, R.string.start_error_message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sign_in:
                loginUser();
                break;

            case R.id.sign_up:
               LaunchManager.showSignUpScreen(this);
                break;
        }
    }
}
