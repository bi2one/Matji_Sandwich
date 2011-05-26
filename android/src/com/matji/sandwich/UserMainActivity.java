package com.matji.sandwich;

import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.ImageDownloader;
import com.matji.sandwich.http.util.MatjiImageDownloader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class UserMainActivity extends Activity{
	Intent intent;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_main);

		intent = getIntent();
		User user = intent.getParcelableExtra("user");
		Log.d("Matji", user.getNick() + "'s Page(user_id:" + user.getId() + ")");

		MatjiImageDownloader downloader = new MatjiImageDownloader();
		downloader.downloadUserImage(user.getId(), (ImageView) findViewById(R.id.user_main_user_image));

		((TextView) findViewById(R.id.user_main_title)).setText(user.getTitle());
		((TextView) findViewById(R.id.user_main_grade)).setText("다이아몬드"); // TODO
		((TextView) findViewById(R.id.user_main_follow_button)).setText(getString(
				user.isFollowing() ? 
						R.string.default_string_unfollow 
						:R.string.default_string_follow));
		((TextView) findViewById(R.id.user_main_follower_count)).setText(getString(R.string.user_main_follower) + ": " + user.getFollowerCount());
		((TextView) findViewById(R.id.user_main_following_count)).setText(getString(R.string.user_main_following) + ": " + user.getFollowingCount());
		((TextView) findViewById(R.id.user_main_intro)).setText((user.getIntro() == null) ? getString(R.string.user_main_not_found_intro) : user.getIntro());
	}
}