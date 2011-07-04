package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.util.ModelType;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class StoreTabActivity extends BaseTabActivity {
	private Intent mainIntent;
	private Intent postIntent;
	private Intent menuIntent;
	private Intent imageIntent;
	private Intent moreIntent;
	private Store store;

	public static final int MAIN_PAGE = 0;
	public static final int MENU_PAGE = 1;
	public static final int MEMO_PAGE = 2;
	public static final int IMAGE_PAGE = 3;
	public static final int MORE_PAGE = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tab);
		
		store = (Store) (SharedMatjiData.getInstance().top());

		mainIntent = new Intent(this, StoreMainActivity.class);
		menuIntent = new Intent(this, StoreMenuActivity.class);
		postIntent = new Intent(this, StorePostListActivity.class);
		imageIntent = new Intent(this, ImageListActivity.class);
		imageIntent.putExtra("id", store.getId());
		imageIntent.putExtra("type", ModelType.STORE);
		moreIntent = new Intent(this, StoreMoreActivity.class);

		TabHost tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("main")
				.setIndicator("메인")
				.setContent(mainIntent));
		tabHost.addTab(tabHost.newTabSpec("menu")
				.setIndicator("메뉴")
				.setContent(menuIntent));
		tabHost.addTab(tabHost.newTabSpec("memo")
				.setIndicator("메모")
				.setContent(postIntent));
		tabHost.addTab(tabHost.newTabSpec("image")
				.setIndicator("이미지")
				.setContent(imageIntent));
		tabHost.addTab(tabHost.newTabSpec("more")
				.setIndicator("기타")
				.setContent(moreIntent));
	}

	public void finish() {
		super.finishWithMatjiData();
	}
}