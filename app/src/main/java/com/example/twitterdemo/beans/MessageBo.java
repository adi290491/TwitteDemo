package com.example.twitterdemo.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aditya.sawant on 13-11-2016.
 */
public class MessageBo {

    @SerializedName("messages")
    public final List<Message> messages;

    public MessageBo(List<Message> messages){
        this.messages = messages;
    }


}
