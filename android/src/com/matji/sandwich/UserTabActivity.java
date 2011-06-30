package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.util.ModelType;

import android.content.Intent;
import android.os.Bundle;

public class UserTabActivity extends BaseTabActivity {
	private Intent mainIntent;
	private Intent storeListIntent;
	private Intent postListIntent;
	private Intent imageIntent;
	private Intent moreIntent;
	private User user;

	public static final int MAIN_PAGE = 0;
	public static final int JJIM_STORE_PAGE = 1;
	public static final int MEMO_PAGE = 2;
	public static final int IMAGE_PAGE = 3;
	public static final int SETTING_PAGE = 4;

	public void finish() {
	    super.finishWithMatjiData();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = (User) SharedMatjiData.getInstance().top();
		
		mainIntent = new Intent(this, UserMainActivity.class);
		storeListIntent = new Intent(this, UserStoreListActivity.class);
		storeListIntent.putExtra("user_id", user.getId());
		postListIntent = new Intent(this, UserPostListActivity.class);
		postListIntent.putExtra("user_id", user.getId());
		imageIntent = new Intent(this, ImageListActivity.class);
		imageIntent.putExtra("id", user.getId());
		imageIntent.putExtra("type", ModelType.USER);
		moreIntent = new Intent(this, StoreMoreActivity.class);

		tabHost.addTab(tabHost.newTabSpec("main")
				.setIndicator("메인")
				.setContent(mainIntent));
		tabHost.addTab(tabHost.newTabSpec("store")
				.setIndicator("맛집")
				.setContent(storeListIntent));
		tabHost.addTab(tabHost.newTabSpec("post")
				.setIndicator("메모")
				.setContent(postListIntent));
		tabHost.addTab(tabHost.newTabSpec("image")
				.setIndicator("이미지")
				.setContent(imageIntent));
		tabHost.addTab(tabHost.newTabSpec("more")
				.setIndicator("기타")
				.setContent(moreIntent));
	}
}