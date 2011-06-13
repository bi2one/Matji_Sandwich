package com.matji.sandwich;

import com.matji.sandwich.GridGalleryActivity.AttachFileType;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.User;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TabHost;

public class UserTabActivity extends BaseTabActivity {
	private TabHost tabHost;
	private Intent intent;
	private Intent mainIntent;
	private Intent menuIntent;
	private Intent postIntent;
	private Intent imageIntent;
	private Intent moreIntent;
	private User user;

	public static final int MAIN_PAGE = 0;
	public static final int MENU_PAGE = 1;
	public static final int MEMO_PAGE = 2;
	public static final int IMAGE_PAGE = 3;
	public static final int SETTING_PAGE = 4;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		tabHost = getTabHost();
		intent = getIntent();
		user = (User) SharedMatjiData.getInstance().top();
		
		createIntent();
		setTabHost();
	}

	private void createIntent() {
		mainIntent = new Intent(this, UserMainActivity.class);
		menuIntent = new Intent(this, StoreMoreActivity.class);
		postIntent = new Intent(this, UserPostActivity.class);
		postIntent.putExtra("user_id", user.getId());
		imageIntent = new Intent(this, GridGalleryActivity.class);
		imageIntent.putExtra(GridGalleryActivity.ATTACH_FILE_TYPE, AttachFileType.USERS);
		imageIntent.putExtra("user_id", user.getId());
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
	
	protected void syncUser(User user) {
		this.user = user;
	}
	

	public void finish() {
	    super.finishWithMatjiData();
	}
}