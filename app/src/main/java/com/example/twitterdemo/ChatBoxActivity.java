package com.example.twitterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ChatBoxActivity extends AppCompatActivity {

    private String userName, userId, screenName, profilePicUrl;
    private Toolbar mToolbar;
    private TextView mToolbarTitle, mToolbarScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat_box);

        userName = getIntent().getStringExtra("user_name");
        userId = getIntent().getStringExtra("user_id");
        screenName = getIntent().getStringExtra("screen_name");
        profilePicUrl = getIntent().getStringExtra("user_profile_pic");

        initToolbar();

        Log.e("details: ", userName + " , " + userId + " , " + screenName + " , " + profilePicUrl);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_user_name);
        mToolbarTitle.setText(userName);
        mToolbarScreenName = (TextView) mToolbar.findViewById(R.id.toolbar_screen_name);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
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
