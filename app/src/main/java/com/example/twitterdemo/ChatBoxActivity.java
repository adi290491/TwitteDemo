package com.example.twitterdemo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twitterdemo.beans.Message;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import retrofit2.Call;

public class ChatBoxActivity extends BaseActivity implements View.OnClickListener{

    private String userName, userId, screenName, profilePicUrl;
    private Toolbar mToolbar;
    private TextView mToolbarTitle, mToolbarScreenName;
    private EditText etTypeMsg;
    private Button btSendMessage;
    private LinearLayout container;

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

        init();

        new ReceiveDirectMessageTask().execute();

        Log.e("details: ", userName + " , " + userId + " , " + screenName + " , " + profilePicUrl);
    }

    private void init() {
        etTypeMsg = (EditText) findViewById(R.id.et_type_msg);
        btSendMessage = (Button) findViewById(R.id.bt_send_message);
        btSendMessage.setOnClickListener(this);

        container = (LinearLayout) findViewById(R.id.text_container);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_user_name);
        mToolbarTitle.setText(userName);
        mToolbarScreenName = (TextView) mToolbar.findViewById(R.id.toolbar_screen_name);
        mToolbarScreenName.setText(screenName);
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

    class ReceiveDirectMessageTask extends AsyncTask<Void, Void, ArrayList<String>>{

        ArrayList<String> receivedmessages = new ArrayList<>();
        ArrayList<String> sentmessages = new ArrayList<>();

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            Call<List<Message>> receiveDMCall = new MyTwitterApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession()).getCustomService().showDirectMessage();
            receiveDMCall.enqueue(new Callback<List<Message>>() {
                @Override
                public void success(Result<List<Message>> result) {
                    List<Message> messageList = result.data;
                    System.out.println("Received message : " + result.data.toString());

                    if(messageList.size() > 0){
                        for(Message msg : messageList){
                            receivedmessages.add(msg.getText());
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Collections.reverse(receivedmessages);
                            for(String text: receivedmessages){
                                showReceivedMessage(text);
                            }
                        }
                    });

                }

                @Override
                public void failure(TwitterException exception) {
                    exception.printStackTrace();
                    System.out.println("Failure message : " + exception.getMessage());
                }
            });

            Call<List<Message>> receiveSentMessageCall = new MyTwitterApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession()).getCustomService().showSentMessages();
            receiveSentMessageCall.enqueue(new Callback<List<Message>>() {
                @Override
                public void success(Result<List<Message>> result) {
                    List<Message> messageList = result.data;
                    System.out.println("Received message : " + result.data.toString());

                    if(messageList.size() > 0){
                        for(Message msg : messageList){
                            sentmessages.add(msg.getText());
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Collections.reverse(sentmessages);
                            for(String text: sentmessages){
                                showSentMessage(text);
                            }
                        }
                    });

                }

                @Override
                public void failure(TwitterException exception) {
                    exception.printStackTrace();
                    System.out.println("Failure message : " + exception.getMessage());
                }
            });
            return receivedmessages;
        }

        @Override
        protected void onPostExecute(ArrayList<String> msgList) {
            for(String text: msgList){
                showReceivedMessage(text);
            }
            super.onPostExecute(msgList);
        }
    }

    private void showReceivedMessage(String text) {
        TextView messageView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        params.setMargins((int)getResources().getDimension(R.dimen.margin_10), (int)getResources().getDimension(R.dimen.margin_10), (int)getResources().getDimension(R.dimen.margin_10), (int)getResources().getDimension(R.dimen.margin_10));
        messageView.setText(text);
        messageView.setBackgroundResource(R.drawable.all_rounded_corner);
        messageView.setGravity(Gravity.CENTER);
        messageView.setPadding((int)getResources().getDimension(R.dimen.padding_8), (int)getResources().getDimension(R.dimen.padding_8), (int)getResources().getDimension(R.dimen.padding_8), (int)getResources().getDimension(R.dimen.padding_8));
        messageView.setTextSize(getResources().getDimension(R.dimen.text_size_16dp));
        messageView.setLayoutParams(params);
        container.addView(messageView);
    }

    private void showSentMessage(String text) {
        TextView messageView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.setMargins((int)getResources().getDimension(R.dimen.margin_10), (int)getResources().getDimension(R.dimen.margin_10), (int)getResources().getDimension(R.dimen.margin_10), (int)getResources().getDimension(R.dimen.margin_10));
        messageView.setText(text);
        messageView.setBackgroundResource(R.drawable.all_rounded_corner);
        messageView.setGravity(Gravity.CENTER);
        messageView.setPadding((int)getResources().getDimension(R.dimen.padding_8), (int)getResources().getDimension(R.dimen.padding_8), (int)getResources().getDimension(R.dimen.padding_8), (int)getResources().getDimension(R.dimen.padding_8));
        messageView.setTextSize(getResources().getDimension(R.dimen.text_size_16dp));
        messageView.setLayoutParams(params);
        container.addView(messageView);
    }

    @Override
    public void onClick(View v) {
        final String textMessage = etTypeMsg.getText().toString();
        if(!TextUtils.isEmpty(textMessage)){

            // The factory instance is re-useable and thread safe.
           new Thread(){

               @Override
               public void run() {
                   super.run();
                   Call<Message> response = new MyTwitterApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession()).getCustomService().sendDirectMessage(textMessage, screenName);
                   response.enqueue(new Callback<Message>() {
                       @Override
                       public void success(Result<Message> result) {

                           Message message = result.data;
                           final String msg = message.getText();
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   showSentMessage(msg);
                                   etTypeMsg.setText("");
                               }
                           });
                       }

                       @Override
                       public void failure(TwitterException exception) {
                           exception.printStackTrace();
                       }
                   });
               }
           }.start();
        }
    }
}
