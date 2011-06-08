package com.matji.sandwich;

import com.matji.sandwich.widget.UserPostListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UserPostActivity extends Activity {
	private Intent intent;
	private int user_id;		
	private UserPostListView listView;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_post);
		
		intent = getIntent();
		user_id = intent.getIntExtra("user_id", 0);
		
		listView = (UserPostListView) findViewById(R.id.user_post_list_view);
		listView.setUserId(user_id);
		listView.setActivity(this);
		listView.requestReload();
	}
}