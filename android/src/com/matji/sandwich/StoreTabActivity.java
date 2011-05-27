package com.matji.sandwich;

import com.matji.sandwich.data.Store;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TabHost;

public class StoreTabActivity extends TabActivity{
	TabHost tabHost;
	Intent intent;
	Intent mainIntent;
	Intent postIntent;
	Intent menuIntent;
	Intent imageIntent;
	Intent moreIntent;
	Store store;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		tabHost = getTabHost();
		intent = getIntent();
		store = intent.getParcelableExtra("store");

		createIntent();
		setTabHost();
	}

	private void createIntent() {
		mainIntent = new Intent(this, StoreMainActivity.class);
		mainIntent.putExtra("store", (Parcelable)store);
		postIntent = new Intent(this, StorePostActivity.class);
		postIntent.putExtra("store_id", store.getId());
		menuIntent = new Intent(this, StoreMoreActivity.class);
		imageIntent = new Intent(this, StoreMoreActivity.class);
		moreIntent = new Intent(this, StoreMoreActivity.class);
	}

	private void setTabHost() {
		tabHost.addTab(tabHost.newTabSpec("main")
				.setIndicator("메인")
				.setContent(mainIntent));
		tabHost.addTab(tabHost.newTabSpec("memo")
				.setIndicator("메모")
				.setContent(postIntent));
		tabHost.addTab(tabHost.newTabSpec("menu")
				.setIndicator("메뉴")
				.setContent(menuIntent));
		tabHost.addTab(tabHost.newTabSpec("image")
				.setIndicator("이미지")
				.setContent(imageIntent));
		tabHost.addTab(tabHost.newTabSpec("more")
				.setIndicator("More")
				.setContent(moreIntent));
	}
}

