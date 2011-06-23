package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StorePostListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StorePostListActivity extends BaseActivity {
	private int store_id;
	private StorePostListView listView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_post);
		store_id = getIntent().getIntExtra("store_id", 0);

		listView = (StorePostListView) findViewById(R.id.store_post_list);
		listView.setStoreId(store_id);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected void onResume() {
		super.onResume();
		listView.dataRefresh();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case WRITE_POST_ACTIVITY:
			if (resultCode == RESULT_OK) {
				listView.requestReload();
			}
			break;
		}
	}

	@Override
	protected String titleBarText() {
		return "StorePostListActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		button.setText("Write");

		return true;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		if (loginRequired()) {
			Intent intent = new Intent(getApplicationContext(), WritePostActivity.class);
			intent.putExtra("store_id", store_id);
			startActivityForResult(intent, WRITE_POST_ACTIVITY);
		}
	}
}