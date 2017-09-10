package com.example.karan.news.firebase_essentials;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;
import com.example.karan.news.R;
import com.example.karan.news.activities.LoginActivity;
import com.example.karan.news.activities.NewsHomeActivity;
import com.example.karan.news.models.User;
import com.example.karan.news.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**This class covers all the aspects of a user's details ie. logging in an
 * existing user or registering a new user in the app's list of users and
 * logging out a user from the app*/

public class FirebaseAuthentication {

    private Context mContext;
    private FirebaseAuth firebaseAuth;

    //Constructor for the class.
    public FirebaseAuthentication(Context context) {
        this.mContext = context;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /*If a new user wants to log in to the app, they need to create an account which requires an email and a
    * password which is handled by this method*/
    public void newUser(final String email, final String password, final ProgressDialog progressDialog){

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(mContext,R.string.toast_email_error,Toast.LENGTH_LONG).show();
        } else {

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {

                        progressDialog.hide();
                        Toast.makeText(mContext, R.string.toast_auth_failed, Toast.LENGTH_SHORT).show();
                    } else {

                        /*get current user UID ie. User's ID which is a unique key given by firebase
                        to every user registered with the app*/

                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        String UID = currentUser.getUid();

                        DatabaseReference mDatabase;

                        // create a database reference for the user
                        mDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.USERS_KEY).child(UID);

                        User user = new User(email, UID);

                        // store user details to firebase database
                        mDatabase.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    // dismiss progress dialog
                                    progressDialog.dismiss();

                                    // launch HomeScreenActivity when user registration is complete
                                    Intent mainActivity = new Intent(mContext, NewsHomeActivity.class);
                                    mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    mContext.startActivity(mainActivity);


                                    // finish current activity
                                    ((Activity) mContext).finish();
                                } else {

                                    // display error message
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext, R.string.toast_auth_failed, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    //Checks user entered credentials and matches with the existing list of app users.
    public void loginUser(String email, String password, final ProgressDialog mProgressDialog) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    // dismiss progress dialog
                    mProgressDialog.dismiss();
                    // launch HomeScreenActivity when user registration is complete
                    Intent mainActivity = new Intent(mContext, NewsHomeActivity.class);
                    mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(mainActivity);
                    // finish current activity
                    ((Activity) mContext).finish();
                } else {

                    // display error message
                    mProgressDialog.dismiss();
                    Toast.makeText(mContext, R.string.toast_auth_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*This method returns unique user id or UID given by firebase to each user registered with the app*/
    public String getCurrentUser() {

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            return null;
        }
    }

    //Logs existing user out from the app.
    public void logoutUser() {

        // sign out from firebase authentication
        firebaseAuth.signOut();

        // launch start activity
        Intent startIntent = new Intent(mContext, LoginActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(startIntent);

        // finish current activity
        ((Activity) mContext).finish();
    }
}
