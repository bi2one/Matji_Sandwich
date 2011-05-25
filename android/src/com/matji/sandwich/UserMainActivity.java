package com.matji.sandwich;

import com.matji.sandwich.data.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserMainActivity extends Activity{
	Intent intent;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_main);

		intent = getIntent();
		User user = intent.getParcelableExtra("user");

		((TextView) findViewById(R.id.user_main_nick)).setText("Nick: " + user.getNick());
		((TextView) findViewById(R.id.user_main_email)).setText("Email: " + user.getEmail());
		((TextView) findViewById(R.id.user_main_userid)).setText("ID: " + user.getUserid());
		((TextView) findViewById(R.id.user_main_intro)).setText("Intro: " + user.getIntro());
		((TextView) findViewById(R.id.user_main_title)).setText("Title: " + user.getTitle());
		((TextView) findViewById(R.id.user_main_follower_count)).setText("Follower Count: " + user.getFollowerCount() + "");
		((TextView) findViewById(R.id.user_main_following_count)).setText("Following Count: " + user.getFollowingCount() + "");
	}
}
