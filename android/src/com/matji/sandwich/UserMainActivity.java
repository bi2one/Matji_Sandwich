package com.matji.sandwich;

import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserMainActivity extends Activity{
	private Intent intent;
	private User user;
	private MatjiImageDownloader downloader;
	
	private TextView title;
	private TextView grade;
	private Button followButton;
	private TextView followerCount;
	private TextView followingCount;
	private TextView intro;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_main);

		intent = getIntent();
		user = intent.getParcelableExtra("user");
		downloader = new MatjiImageDownloader();
		
		setUserInfo();
	}
	
	private void setUserInfo() {
		title = (TextView) findViewById(R.id.user_main_title);		
		grade = (TextView) findViewById(R.id.user_main_grade);
		followButton = (Button) findViewById(R.id.user_main_follow_button);
		followerCount = (TextView) findViewById(R.id.user_main_follower_count);
		followingCount = (TextView) findViewById(R.id.user_main_following_count);
		intro = (TextView) findViewById(R.id.user_main_intro);
		
		downloader.downloadUserImage(user.getId(), (ImageView) findViewById(R.id.user_main_user_image));
		title.setText(user.getTitle());
		grade.setText("다이아몬드"); // TODO
		followButton.setText(getString(
				user.isFollowing() ?
						R.string.default_string_unfollow
						:R.string.default_string_follow));
		followerCount.setText(getString(R.string.user_main_follower) + ": " + user.getFollowerCount());
		followingCount.setText(getString(R.string.user_main_following) + ": " + user.getFollowingCount());
		intro.setText((user.getIntro() == null) ? getString(R.string.user_main_not_found_intro) : user.getIntro());

	}
}