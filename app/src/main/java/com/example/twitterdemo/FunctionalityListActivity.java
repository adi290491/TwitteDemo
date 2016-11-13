package com.example.twitterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class FunctionalityListActivity extends BaseActivity implements View.OnClickListener {

    TextView tv_post_tweet, tv_get_timeline, tv_search_tweets, tv_get_followers, tv_direct_message;
    TwitterSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionality_list);
        tv_post_tweet = (TextView) findViewById(R.id.tv_post_tweet);
        tv_post_tweet.setOnClickListener(this);

        tv_get_timeline = (TextView) findViewById(R.id.tv_get_timeline);
        tv_get_timeline.setOnClickListener(this);

        tv_direct_message = (TextView) findViewById(R.id.tv_direct_message);
        tv_direct_message.setOnClickListener(this);

        tv_search_tweets = (TextView) findViewById(R.id.tv_search_tweets);
        tv_search_tweets.setOnClickListener(this);

        tv_get_followers = (TextView) findViewById(R.id.tv_retrieve_followers);
        tv_get_followers.setOnClickListener(this);

        session = Twitter.getSessionManager().getActiveSession();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_post_tweet:

                if(Tools.isNetworkAvailable(this)) {
                    TweetComposer.Builder builder = new TweetComposer.Builder(FunctionalityListActivity.this);
                    builder.show();
                }else{
                    Tools.showToast(this, getString(R.string.check_connection));
                }

                break;

            case R.id.tv_get_timeline:

                intent = new Intent(FunctionalityListActivity.this, UserTimelineActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_direct_message:

                intent = new Intent(FunctionalityListActivity.this, DirectMessageActivity.class);
                intent.putExtra("user_id", session.getUserId());
                intent.putExtra("user_name", session.getUserName());
                startActivity(intent);
                break;

            case R.id.tv_search_tweets:

                intent = new Intent(FunctionalityListActivity.this, SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_retrieve_followers:
                intent = new Intent(FunctionalityListActivity.this, FollowersActivity.class);
                intent.putExtra("user_id", session.getUserId());
                intent.putExtra("user_name", session.getUserName());
                startActivity(intent);
                break;
        }
    }
}
