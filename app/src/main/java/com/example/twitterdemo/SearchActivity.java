package com.example.twitterdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.services.SearchService;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import retrofit2.Call;

public class SearchActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    private boolean flagLoading;
    private boolean endOfSearchResult;
    private static String searchQuery = "#sachintendulkar";
    private static final String search_result_type = "recent";
    private static final int search_count = 20;
    private long maxId;
    ListView searchList;
    private TweetTimelineListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolbar();
        searchList = (ListView) findViewById(R.id.tweet_search);
    }

    public void searchQuery(View view){
        EditText etQuery = (EditText) findViewById(R.id.et_query);
        searchQuery = etQuery.getText().toString();

        if(Tools.isNetworkAvailable(this)) {
            searchTweetsFromQuery();
        }else{
            Tools.showToast(this, getString(R.string.connectivity_error));
        }
    }

    private void searchTweetsFromQuery() {

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        SearchService service = Twitter.getApiClient(session).getSearchService();
        showProgressDialog();
        Call<Search> searchCall = service.tweets(searchQuery, null, null, null, null, 100, null, null, null, null);

        searchCall.enqueue(new Callback<Search>() {
            @Override
            public void success(Result<Search> result) {
                Search search = result.data;

                SearchTimeline searchTimeline = new SearchTimeline.Builder()
                        .query(searchQuery).build();
                adapter = new TweetTimelineListAdapter.Builder(SearchActivity.this)
                        .setTimeline(searchTimeline)
                        .build();

                //setListAdapter(adapter);
                searchList.setAdapter(adapter);

                hideProgressDialog();

            }

            @Override
            public void failure(TwitterException exception) {
                hideProgressDialog();
            }
        });
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.searching));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbarTitle.setText(getString(R.string.search_a_tweet));
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
