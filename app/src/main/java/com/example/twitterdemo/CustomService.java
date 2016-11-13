package com.example.twitterdemo;


import com.example.twitterdemo.beans.Followers;
import com.example.twitterdemo.beans.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by aditya.sawant on 08-11-2016.
 */
public interface CustomService {

    @GET("/1.1/followers/list.json")
    Call<Followers> showFollowersList(@Query("user_id") Long userId, @Query("screen_name") String screeName, @Query("count") int count);

    @GET("/1.1/friends/list.json")
    Call<Followers> showFriendsList(@Query("user_id") Long userId, @Query("screen_name") String screeName, @Query("count") int count);

    @GET("/1.1/direct_messages.json")
    Call<List<Message>> showDirectMessage();

    @GET("/1.1/direct_messages/sent.json")
    Call<List<Message>> showSentMessages();

    @FormUrlEncoded
    @POST("1.1/direct_messages/new.json")
    Call<Message> sendDirectMessage(@Field("text") String text, @Field("screen_name") String screenName);
}
