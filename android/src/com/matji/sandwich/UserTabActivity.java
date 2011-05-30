package com.matji.sandwich;

import com.matji.sandwich.data.User;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TabHost;

public class UserTabActivity extends TabActivity {
	TabHost tabHost;
	Intent intent;
	Intent mainIntent;
	Intent menuIntent;
	Intent postIntent;
	Intent imageIntent;
	Intent moreIntent;
	User user;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		tabHost = getTabHost();
		intent = getIntent();
		user = intent.getParcelableExtra("user");
		
		createIntent();
		setTabHost();
	}

	private void createIntent() {
		mainIntent = new Intent(this, UserMainActivity.class);
		mainIntent.putExtra("user", (Parcelable)user);
		menuIntent = new Intent(this, StoreMoreActivity.class);
		postIntent = new Intent(this, UserPostActivity.class);
		postIntent.putExtra("user_id", user.getId());
		imageIntent = new Intent(this, StoreMoreActivity.class);
		moreIntent = new Intent(this, StoreMoreActivity.class);
	}
	
	private void setTabHost() {
		tabHost.addTab(tabHost.newTabSpec("main")
				.setIndicator("메인")
				.setContent(mainIntent));
		tabHost.addTab(tabHost.newTabSpec("my_store")
				.setIndicator("메뉴")
				.setContent(menuIntent));
		tabHost.addTab(tabHost.newTabSpec("my_post")
				.setIndicator("메모")
				.setContent(postIntent));
		tabHost.addTab(tabHost.newTabSpec("my_image")
				.setIndicator("이미지")
				.setContent(imageIntent));
		tabHost.addTab(tabHost.newTabSpec("more")
				.setIndicator("설정")
				.setContent(moreIntent));
	}
}
