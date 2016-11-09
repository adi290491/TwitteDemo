package com.example.twitterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.twitterdemo.beans.Followers;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;

public class DirectMessageActivity extends AppCompatActivity  {
    private ListView lvFollowers;
    private long userId;
    private String userName;
    private FriendsListAdapter adapter;

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
                Log.e("success", "success");
               // Object object = result.data;
               // Log.e("json_object : ", object.toString());
                final Followers followers = result.data;

                adapter = new FriendsListAdapter(DirectMessageActivity.this, followers.users);
                lvFollowers.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("failure", "filure");
            }
        });

        lvFollowers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = (User) parent.getItemAtPosition(position);
                Intent messageIntent = new Intent(DirectMessageActivity.this, ChatBoxActivity.class);
                messageIntent.putExtra("user_name", selectedUser.name);
                messageIntent.putExtra("screen_name", selectedUser.screenName);
                messageIntent.putExtra("user_id", selectedUser.idStr);
                messageIntent.putExtra("user_profile_pic", selectedUser.profileImageUrl);

                startActivity(messageIntent);
            }
        });
    }

}
