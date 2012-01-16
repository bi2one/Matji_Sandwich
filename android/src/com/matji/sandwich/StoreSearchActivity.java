package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.search.RecentSearchListView;
import com.matji.sandwich.widget.search.StoreSearchListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class StoreSearchActivity extends BaseActivity implements Searchable, ActivityStartable {
	private StoreSearchListView searchView;
	private RecentSearchListView recentView;

    public int setMainViewId() {
	return R.id.activity_store_search;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		setContentView(R.layout.activity_store_search);
		
		recentView = (RecentSearchListView) findViewById(R.id.RecentSearchListView);
		recentView.setActivity(this);
		searchView = (StoreSearchListView) findViewById(R.id.StoreSearchListView);
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
        case STORE_MAIN_ACTIVITY: case USER_MAIN_ACTIVITY: case IMAGE_SLIDER_ACTIVITY:
            if (resultCode == Activity.RESULT_OK)
                setIsFlow(true);
            break;
        }
	}
}
