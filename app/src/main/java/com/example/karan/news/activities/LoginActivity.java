package com.example.karan.news.activities;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karan.news.R;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;
import com.example.karan.news.utils.LaunchManager;


/**
 * Created by karan on 7/8/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText pass,email;
    private Button login,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        user=(Button) findViewById(R.id.sign_up);

        pass=(EditText) findViewById(R.id.pass);
        email=(EditText)findViewById(R.id.email);

        login=(Button) findViewById(R.id.sign_in);

        login.setOnClickListener(this);
        user.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void loginUser() {

        // setup progress dialog
        ProgressDialog mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setTitle(getString(R.string.start_progress_dialog_title));
        mProgressDialog.setMessage(getString(R.string.start_progress_dialog_message));
        mProgressDialog.setCanceledOnTouchOutside(false);

        String mEmail = email.getText().toString();
        String mPass = pass.getText().toString();

        if (!(TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPass))) {

            // show progress dialog
            mProgressDialog.show();
            // login registered user
            FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(this);
            firebaseAuthentication.loginUser(mEmail, mPass, mProgressDialog);

            SharedPreferences sharedPreferences=getSharedPreferences("myPreferences",MODE_PRIVATE);
            sharedPreferences.getString(mEmail,"userName");
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
