package com.example.twitterdemo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.twitterdemo.beans.Followers;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;


public class FollowersActivity extends BaseActivity {

    private ListView lvFollowers;
    private long userId;
    private String userName;
    private FollowersListAdapter adapter;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        initToolbar();

        lvFollowers = (ListView) findViewById(R.id.lv_followers);
        userId = getIntent().getLongExtra("user_id", 0);
        userName = getIntent().getStringExtra("user_name");
        Log.e("username: ", userName);

        if(Tools.isNetworkAvailable(this)) {
            showProgressDialog();

            MyTwitterApiClient apiClient = new MyTwitterApiClient(Twitter.getSessionManager().getActiveSession());
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
                    hideProgressDialog();
                }

                @Override
                public void failure(TwitterException exception) {
                    hideProgressDialog();
                }
            });
        }else{
            Tools.showToast(this, getString(R.string.connectivity_error));
        }

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbarTitle.setText(getResources().getString(R.string.followers_list));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
