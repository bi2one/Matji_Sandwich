package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.widget.search.PostSearchListView;
import com.matji.sandwich.widget.search.RecentSearchListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PostSearchActivity extends BaseActivity implements Searchable, ActivityStartable {
	private PostSearchListView searchView;
	private RecentSearchListView recentView;
	
    public int setMainViewId() {
	return R.id.activity_post_search;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		setContentView(R.layout.activity_post_search);

		recentView = (RecentSearchListView) findViewById(R.id.RecentSearchListView);
		recentView.setActivity(this);
		searchView = (PostSearchListView) findViewById(R.id.PostSearchListView);
		searchView.setActivity(this);
		searchView.setVisibility(View.GONE);
	}
	
	@Override
	public void search(String keyword) { 
		recentView.setVisibility(View.GONE);
		searchView.search(keyword);
		searchView.setVisibility(View.VISIBLE);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_reload:
            searchView.refresh();
            return true;
        }
        return false;
    }

	@Override
	public void activityResultDelivered(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case POST_MAIN_ACTIVITY:
            if (resultCode == RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostMainActivity.POSTS);
                searchView.setPosts(posts);
                setIsFlow(true);
            }
            break;
        case STORE_MAIN_ACTIVITY: case USER_MAIN_ACTIVITY: case IMAGE_SLIDER_ACTIVITY:
            if (resultCode == Activity.RESULT_OK)
                setIsFlow(true);
            break;
        }
	}
}
