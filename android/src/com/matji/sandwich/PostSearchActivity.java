package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.PostSearchListView;
import com.matji.sandwich.widget.SearchInputBar.Searchable;

import android.os.Bundle;

public class PostSearchActivity extends BaseActivity implements Searchable {
	private PostSearchListView searchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void init() {
		super.init();
		setContentView(R.layout.activity_post_search);
		
		searchView = (PostSearchListView) findViewById(R.id.PostSearchListView);
		searchView.setActivity(this);
	}
	
	@Override
	public void search(String keyword) { 
		searchView.search(keyword);
	}
}
