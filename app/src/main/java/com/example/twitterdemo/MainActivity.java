package com.example.twitterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;


public class MainActivity extends AppCompatActivity {



    private TwitterLoginButton loginButton;
    private TwitterAuthClient authClient;
    public static TwitterSession session;
    public static TwitterAuthConfig authConfig;
    private String userName;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new TweetComposer());
        setContentView(R.layout.activity_main);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                session = result.data;
                userName = result.data.getUserName();
                userId = result.data.getUserId();
                String token = result.data.getAuthToken().token;
                String secret = result.data.getAuthToken().secret;

                AccountService ac = Twitter.getApiClient(session).getAccountService();
                Call<User> userCall = ac.verifyCredentials(true, true);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {

                        Log.e("success:: ", "credential verified");
                        Toast.makeText(MainActivity.this, userName, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, FunctionalityListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.e("failed:: ", "credential failed");
                    }
                });


            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
