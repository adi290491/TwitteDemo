package com.example.twitterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.twitterdemo.beans.Followers;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;


public class FollowersActivity extends AppCompatActivity {

    private ListView lvFollowers;
    private long userId;
    private String userName;
    private FollowersListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        lvFollowers = (ListView) findViewById(R.id.lv_followers);
        userId = getIntent().getLongExtra("user_id", 0);
        userName = getIntent().getStringExtra("user_name");
        Log.e("username: ", userName);

        MyTwitterApiClient apiClient = new MyTwitterApiClient(MainActivity.session);
        Call<Followers> call = apiClient.getCustomService().showFollowersList(userId, userName, 200);

        call.enqueue(new Callback<Followers>() {
            @Override
            public void success(Result<Followers> result) {
                Followers followers = result.data;
                Log.e("followers ", followers.users.toString());
                User user = followers.users.get(0);
                Log.e("user: ", user.screenName + " , " + user.name);
                adapter = new FollowersListAdapter(FollowersActivity.this, followers.users);

                lvFollowers.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
       /* try {
            fetchFollowersList();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    private void fetchFollowersList() throws JSONException {
        RequestParams params = new RequestParams();
        params.add("user_id", String.valueOf(userId));
        params.add("cursor", String.valueOf(-1));
        TwitterRestClient.get("followers/list.json", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Log.e("followers:: ", response.toString());
                onServerResponse(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("failed:: ", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("failed_array:: ", errorResponse.toString());
            }

        });
    }

    private void onServerResponse(JSONArray response) {
        List<Followers> ls = null;
        try {
            ls = parseResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Followers> parseResponse(JSONArray response) {
        List<Followers> mFollowersList = new ArrayList<>();
        /*if(response.h)*/
        return mFollowersList;
    }

}
