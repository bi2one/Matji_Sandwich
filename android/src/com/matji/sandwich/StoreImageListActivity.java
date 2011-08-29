package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StoreImageListView;

import android.os.Bundle;

public class StoreImageListActivity extends BaseActivity {
	public static final String STORE = "store";

    public int setMainViewId() {
	return R.id.activity_store_image_list;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_image_list);

		StoreImageListView listView = (StoreImageListView) findViewById(R.id.store_image_list_view);
		listView.setStore((Store) getIntent().getParcelableExtra(STORE));
		listView.setActivity(this);
		listView.requestReload();
	}
}