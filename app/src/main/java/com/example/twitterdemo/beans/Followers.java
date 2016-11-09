package com.example.twitterdemo.beans;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by aditya.sawant on 08-11-2016.
 */
public class Followers {

    @SerializedName("users")
    public final List<User> users;

    public Followers(List<User> users) {
        this.users = users;
    }


}
