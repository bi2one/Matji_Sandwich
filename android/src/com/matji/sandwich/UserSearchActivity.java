package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.search.UserSearchListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

import android.os.Bundle;

public class UserSearchActivity extends BaseActivity implements Searchable {
	private UserSearchListView searchView;

    public int setMainViewId() {
	return R.id.activity_user_search;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void init() {
		super.init();
		setContentView(R.layout.activity_user_search);
		
		searchView = (UserSearchListView) findViewById(R.id.UserSearchListView);
		searchView.setActivity(this);
	}
	
	@Override
	public void search(String keyword) { 
		searchView.search(keyword);
	}
}
