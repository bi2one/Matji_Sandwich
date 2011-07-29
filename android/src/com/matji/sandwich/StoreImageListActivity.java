package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StoreImageListView;

import android.os.Bundle;

public class StoreImageListActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_image_list);

		StoreImageListView listView = (StoreImageListView) findViewById(R.id.user_image_list_view);
		listView.setActivity(this);
		listView.requestReload();
	}
}