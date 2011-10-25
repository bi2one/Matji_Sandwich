package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.search.StoreSearchListView;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class StoreSearchActivity extends BaseActivity implements Searchable {
	private StoreSearchListView searchView;

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
		
		searchView = (StoreSearchListView) findViewById(R.id.StoreSearchListView);
		searchView.setActivity(this);
	}
	
	@Override
	public void search(String keyword) { 
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
