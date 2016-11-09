package com.example.twitterdemo.beans;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.Search;

import java.util.List;

/**
 * Created by aditya.sawant on 08-11-2016.
 */
public class Searches {

    @SerializedName("search")
    public final List<Search> search;

    public Searches(List<Search> searches) {
        this.search = searches;
    }

}
