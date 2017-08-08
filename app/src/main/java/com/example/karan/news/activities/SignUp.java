package com.example.karan.news.activities;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by karan on 7/9/2017.
 */

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText newUser,newPass,conPass;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        newUser=(EditText) findViewById(R.id.new_email);
        newPass=(EditText) findViewById(R.id.new_pass);
        conPass=(EditText) findViewById(R.id.con_pass);

        submit=(Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    private void registerUser() {

        // setup progress dialog
        ProgressDialog mProgressDialog = new ProgressDialog(SignUp.this);

        mProgressDialog.setTitle("New");
        mProgressDialog.setMessage("New");
        mProgressDialog.setCanceledOnTouchOutside(false);

        String email = newUser.getText().toString().trim();
        String password = newPass.getText().toString().trim();
        String passcon=conPass.getText().toString().trim();

        if(!newPass.equals(conPass))
        {
            Toast.makeText(this,R.string.passwords_differ,Toast.LENGTH_SHORT);
        }

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Enter user details",Toast.LENGTH_SHORT).show();
        }

        if (!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passcon))) {

            mProgressDialog.setMessage(getString(R.string.wait_message));
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            // register user to firebase authentication
            // store name and email to firebase database
            FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(SignUp.this);
            firebaseAuthentication.newUser(email,password,passcon,mProgressDialog);
        }
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("user_new",email);
        edit.commit();
    }

    @Override
    public void onBackPressed() {
        LaunchManager.showSignInScreen(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                registerUser();
            break;
        }
    }
}
