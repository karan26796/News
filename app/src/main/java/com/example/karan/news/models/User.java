package com.example.karan.news.models;

/**This model class is used to fetch user credentials like their email and username which store
 * user's UID and their email in the respective fields*/

public class User {

    public String username;
    public String email;

    public User(String username, String email) {

        this.username = username;
        this.email = email;
    }

    public User() {
    }
}
