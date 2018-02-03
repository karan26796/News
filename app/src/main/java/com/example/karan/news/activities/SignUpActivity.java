package com.example.karan.news.activities;

import android.app.ProgressDialog;
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
import com.example.karan.news.firebasemanager.FirebaseAuthentication;
import com.example.karan.news.utils.LaunchManager;

/**
 * Created by karan on 7/9/2017.
 * Activity lets user enter details and add to the app's user list.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNewUser,mNewPass,mConPass;
    private Button mSubmitButton;
    private Window window;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        window=getWindow();
        mNewUser=(EditText) findViewById(R.id.sign_up_new_email);
        mNewPass=(EditText) findViewById(R.id.sign_up_new_pass);
        mConPass=(EditText) findViewById(R.id.sign_up_con_pass);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.gray));
        mSubmitButton=(Button) findViewById(R.id.btn_submit);
        mSubmitButton.setOnClickListener(this);
    }

    //Method to register user to the app's user list
    private void registerUser() {

        // setup progress dialog
        ProgressDialog mProgressDialog = new ProgressDialog(SignUpActivity.this);
        mProgressDialog.setCanceledOnTouchOutside(false);

        String email = mNewUser.getText().toString().trim();
        String password = mNewPass.getText().toString().trim();
        String pass_con= mConPass.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this,R.string.toast_enter_details,Toast.LENGTH_SHORT).show();
        }

        if (!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(pass_con))) {

            mProgressDialog.setMessage(getString(R.string.progress_bar_wait_message));
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            // register user to firebase authentication
            // store name and email to firebase database
            FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(SignUpActivity.this);
            firebaseAuthentication.newUser(email,password,mProgressDialog);

        }
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
        LaunchManager.showSignInScreen(this);
    }

    //onCLick method on completion of registration and otherwise
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                //method call to check user credentials
                registerUser();
            break;
        }
    }
}
