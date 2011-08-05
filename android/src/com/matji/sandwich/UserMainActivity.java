package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.RoundTabHost;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

//  User main x -> user talk activity
//public class UserMainActivity extends BaseTabActivity implements Requestable {
public class UserMainActivity extends BaseTabActivity {
	private RoundTabHost tabHost;
	private User user;
	
	public static final String USER = "user";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();

		refresh();
	}
	
	protected void init() {
		super.init();
		
		setContentView(R.layout.activity_user_main);
		
		user = getIntent().getParcelableExtra(USER);

		Intent userPostListIntent = new Intent(this, UserPostListActivity.class);
		userPostListIntent.putExtra(UserPostListActivity.USER, (Parcelable) user);
		
		Intent userImageListIntent = new Intent(this, UserImageListActivity.class);
		userImageListIntent.putExtra(UserImageListActivity.USER, (Parcelable) user);
		
		Intent userUrlListIntent = new Intent(this, UserTagActivity.class);
		userUrlListIntent.putExtra(UserTagActivity.USER, (Parcelable) user);
		
		tabHost = (RoundTabHost) getTabHost();

		tabHost.addLeftTab("tab1",
				R.string.store_main_post_list_view, 
				userPostListIntent);
		tabHost.addCenterTab("tab2",
				R.string.store_main_img,
				userImageListIntent);
		tabHost.addRightTab("tab3",
				R.string.store_main_review,
				userUrlListIntent);

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