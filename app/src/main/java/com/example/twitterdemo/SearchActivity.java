package com.example.twitterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

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

public class SearchActivity extends AppCompatActivity {

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

        searchList = (ListView) findViewById(R.id.tweet_search);
    }

    public void searchQuery(View view){
        EditText etQuery = (EditText) findViewById(R.id.et_query);
        searchQuery = etQuery.getText().toString();
        searchTweetsFromQuery();
    }

    private void searchTweetsFromQuery() {

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        SearchService service = Twitter.getApiClient(session).getSearchService();
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

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

}
