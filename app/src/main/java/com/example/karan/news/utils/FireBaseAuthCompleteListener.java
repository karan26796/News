package com.example.karan.news.utils;
import com.google.firebase.auth.FirebaseUser;
/**
 * Created by karan on 7/13/2017.
 */

public interface FireBaseAuthCompleteListener {
    void onAuthSuccess(FirebaseUser user);
    void onAuthFailure();
}
