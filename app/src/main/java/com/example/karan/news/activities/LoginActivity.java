package com.example.karan.news.activities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.karan.news.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by karan on 7/8/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener/*,FireBaseAuthCompleteListener*/ {

    private EditText pass,email;
    private Button login,user;
    private DatabaseReference dr;
    private ImageView img;
    private String m,n;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        user=(Button) findViewById(R.id.sign_up);
        firebaseAuth=FirebaseAuth.getInstance();
        pass=(EditText) findViewById(R.id.pass);
        email=(EditText)findViewById(R.id.email);

        login=(Button) findViewById(R.id.sign_in);

        img=(ImageView) findViewById(R.id.img);
        login.setOnClickListener(this);
        user.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

   /* public void loginUser() {
        m = email.getText().toString().trim();
        n = pass.getText().toString().trim();
        if (TextUtils.isEmpty(m)) {
            Toast.makeText(getApplicationContext(), "Enter detail(s)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(n)) {
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("user", m);
        edit.commit();
    }*/

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.sign_in:
                //loginUser();
                i = new Intent(LoginActivity.this, NewsActivity.class);
                startActivity(i);
                break;

            case R.id.sign_up:
                i = new Intent(LoginActivity.this, SignUp.class);
                startActivity(i);
                break;
        }
    }
}
