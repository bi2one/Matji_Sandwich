package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.search.RecentSearchListView;
import com.matji.sandwich.widget.search.UserSearchListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class UserSearchActivity extends BaseActivity implements Searchable {
	private UserSearchListView searchView;
	private RecentSearchListView recentView;
	
    public int setMainViewId() {
	return R.id.activity_user_search;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		setContentView(R.layout.activity_user_search);
	
		recentView = (RecentSearchListView) findViewById(R.id.RecentSearchListView);
		recentView.setActivity(this);
		searchView = (UserSearchListView) findViewById(R.id.UserSearchListView);
		searchView.setActivity(this);
	}
	
	@Override
	public void search(String keyword) { 
		recentView.setVisibility(View.GONE);
		searchView.search(keyword);
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
}
