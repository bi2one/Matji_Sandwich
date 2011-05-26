package com.matji.sandwich;

import com.matji.sandwich.data.User;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class UserTabActivity extends TabActivity {
	TabHost tabHost;
	Intent intent;
	Intent mainIntent;
	Intent myStoreIntent;
	Intent myPostIntent;
	Intent myImageIntent;
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
		mainIntent.putExtra("user", user);
		myStoreIntent = new Intent(this, StoreMoreActivity.class);
		myPostIntent = new Intent(this, StoreMoreActivity.class);
		myImageIntent = new Intent(this, StoreMoreActivity.class);
		moreIntent = new Intent(this, StoreMoreActivity.class);
	}
	
	private void setTabHost() {
		tabHost.addTab(tabHost.newTabSpec("main")
				.setIndicator("메인")
				.setContent(mainIntent));
		tabHost.addTab(tabHost.newTabSpec("my_store")
				.setIndicator("메뉴")
				.setContent(new Intent(this, StoreMoreActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("my_post")
				.setIndicator("메모")
				.setContent(new Intent(this, StoreMoreActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("my_image")
				.setIndicator("이미지")
				.setContent(new Intent(this, StoreMoreActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("more")
				.setIndicator("설정")
				.setContent(new Intent(this, StoreMoreActivity.class)));	
	}
}
