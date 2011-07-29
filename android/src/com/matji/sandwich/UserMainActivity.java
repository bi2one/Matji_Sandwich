package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.RoundTabHost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

//  User main x -> user talk activity
//public class UserMainActivity extends BaseTabActivity implements Requestable {
public class UserMainActivity extends BaseTabActivity {
	private RoundTabHost tabHost;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	@Override
	public void onResume() {
		super.onResume();

		refresh();
	}
	
	@Override
	public void finish() {
		super.finishWithMatjiData();
	}
	
	private void init() {
		setContentView(R.layout.activity_user_main);

		tabHost = (RoundTabHost) getTabHost();

		tabHost.addLeftTab("tab1",
				R.string.store_main_post_list_view,
				new Intent(this, UserPostListActivity.class));
		tabHost.addCenterTab("tab2",
				R.string.store_main_img,
				new Intent(this, UserImageListActivity.class));
		tabHost.addRightTab("tab3",
				R.string.store_main_review,
				new Intent(this, UserTagActivity.class));
//		new Intent(this, UserStoreListActivity.class));
		
		LinearLayout wrapper = (LinearLayout) findViewById(R.id.user_talk_wrapper);
		wrapper.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				switch (e.getAction()) {
				case MotionEvent.ACTION_UP:
					Log.d("Matji", "ACTION UP");
				case MotionEvent.ACTION_DOWN:
					Log.d("Matji", "ACTION DOWN");
				}
				return false;
			}
		});
	}
	
	public void refresh() {
		
	}	

	public String getCount(int id, int count) {
		return getString(id) + ": " + count;
	}
	
	public String getCountNumberOf(int id, int count) {
		return getString(id) + ": " + count + getString(R.string.default_string_number_of);
	}
}