package com.example.twitterdemo.beans;

/**
 * Created by aditya.sawant on 13-11-2016.
 */
public class Entities
{
    private String[] urls;

    private String[] hashtags;

    private String[] user_mentions;

    public String[] getUrls ()
    {
        return urls;
    }

    public void setUrls (String[] urls)
    {
        this.urls = urls;
    }

    public String[] getHashtags ()
    {
        return hashtags;
    }

    public void setHashtags (String[] hashtags)
    {
        this.hashtags = hashtags;
    }

    public String[] getUser_mentions ()
    {
        return user_mentions;
    }

    public void setUser_mentions (String[] user_mentions)
    {
        this.user_mentions = user_mentions;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [urls = "+urls+", hashtags = "+hashtags+", user_mentions = "+user_mentions+"]";
    }
}
