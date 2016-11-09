package com.example.twitterdemo;


import com.example.twitterdemo.beans.Followers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aditya.sawant on 08-11-2016.
 */
public interface CustomService {

    @GET("/1.1/followers/list.json")
    Call<Followers> showFollowersList(@Query("user_id") Long userId, @Query("screen_name") String screeName, @Query("count") int count);

    @GET("/1.1/friends/list.json")
    Call<Followers> showFriendsList(@Query("user_id") Long userId, @Query("screen_name") String screeName, @Query("count") int count);
}
