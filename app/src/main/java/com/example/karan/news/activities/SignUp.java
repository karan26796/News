package com.example.karan.news.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karan.news.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by karan on 7/9/2017.
 */

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText newuser,newpass,conpass;
    private String a,b,c;
    private Button submit;
    private Vibrator vibrator;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        newuser=(EditText) findViewById(R.id.new_email);
        newpass=(EditText) findViewById(R.id.new_pass);
        conpass=(EditText) findViewById(R.id.con_pass);

        submit=(Button) findViewById(R.id.submit);

        firebaseAuth=FirebaseAuth.getInstance();
        submit.setOnClickListener(this);
    }

    public void newUser(){

        a = newuser.getText().toString().trim();
        b = conpass.getText().toString().trim();
        c = newpass.getText().toString().trim();

        if(TextUtils.isEmpty(a)){
            Toast.makeText(this,"Enter Email id",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(b)){
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT);
            return;
        }
        if(TextUtils.isEmpty(c)){
            Toast.makeText(this,"Confirm Password",Toast.LENGTH_SHORT);
            return;
        }
        if(b!=c){
            Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT);
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(a,b).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User successfully registered",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_SHORT);
                }
            }
        });
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("user_new",a);
        edit.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit: {

                newUser();
                Intent intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
            }
            break;

        }
    }
}
