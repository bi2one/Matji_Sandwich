package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.SearchInputBar.Searchable;
import com.matji.sandwich.widget.StoreSearchListView;

import android.os.Bundle;

public class StoreSearchActivity extends BaseActivity implements Searchable {
	private StoreSearchListView searchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void init() {
		super.init();
		setContentView(R.layout.activity_store_search);
		
		searchView = (StoreSearchListView) findViewById(R.id.StoreSearchListView);
		searchView.setActivity(this);
	}
	
	@Override
	public void search(String keyword) { 
		searchView.search(keyword);
	}
}
