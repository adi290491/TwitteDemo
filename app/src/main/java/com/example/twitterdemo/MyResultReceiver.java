package com.example.twitterdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

public class MyResultReceiver extends BroadcastReceiver {
    public MyResultReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())){
            //success
            final Long tweetId = intent.getExtras().getLong(TweetUploadService.EXTRA_TWEET_ID);
            if(tweetId!=-1){
                Toast.makeText(context, "Tweet uploaded Successfully", Toast.LENGTH_SHORT).show();
            }
        }else{
            //failure
            final Intent retryIntent = intent.getExtras().getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
            Toast.makeText(context, "Failed to upload tweet", Toast.LENGTH_SHORT).show();
        }
    }
}
