package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StoreUrlListView;

import android.os.Bundle;

public class StoreUrlListActivity extends BaseActivity {
	private StoreUrlListView listView;
	
	public static final String STORE = "store";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_url);

		listView = (StoreUrlListView) findViewById(R.id.store_url_list);
		listView.setUserId(((Store) getIntent().getParcelableExtra(STORE)).getId());
		listView.setActivity(this);
		listView.requestReload();
	}
}