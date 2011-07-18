package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StoreBookmarkedListView;

import android.os.Bundle;

public class UserStoreListActivity extends BaseActivity {
	private StoreBookmarkedListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_store);

		int user_id = getIntent().getIntExtra("user_id", 0);
		
		listView = (StoreBookmarkedListView) findViewById(R.id.store_bookmarked_list);
		listView.setUserId(user_id);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected void onResume() {
		super.onResume();
		listView.dataRefresh();
	}
}