package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.session.Session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserMainActivity extends Activity implements Requestable {
	private Intent intent;
	private User user;
	private MatjiImageDownloader downloader;

	private HttpRequestManager manager;
	private HttpRequest request;
	private Session session;
	private DBProvider dbProvider;

	private TextView title;
	private TextView grade;
	private Button followButton;
	private TextView followerCount;
	private TextView followingCount;
	private TextView intro;

	private static final int FOLLOW_REQUEST = 1;
	private static final int UN_FOLLOW_REQUEST = 2;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_main);

		intent = getIntent();
		user = intent.getParcelableExtra("user");
		downloader = new MatjiImageDownloader();

		manager = new HttpRequestManager(this, this);
		session = Session.getInstance(this);
		dbProvider = DBProvider.getInstance(this);

		initUserInfo();
	}

	private void initUserInfo() {
		title = (TextView) findViewById(R.id.user_main_title);		
		grade = (TextView) findViewById(R.id.user_main_grade);
		followButton = (Button) findViewById(R.id.user_main_follow_btn);
		followerCount = (TextView) findViewById(R.id.user_main_follower_count);
		followingCount = (TextView) findViewById(R.id.user_main_following_count);
		intro = (TextView) findViewById(R.id.user_main_intro);

		title.setText(user.getTitle());

		setUserInfo();
	}

	private void setUserInfo() {
		/* Set User Image */
		downloader.downloadUserImage(user.getId(), (ImageView) findViewById(R.id.user_main_image));
		/* set Grade */
		grade.setText("다이아몬드"); // TODO
		intro.setText(getIntroStr());
		if (session.isLogin()) {
			if (dbProvider.isExistFollowing(user.getId())) {
				followButton.setText(getString(R.string.user_main_unfollow));
			} else {
				followButton.setText(getString(R.string.user_main_follow));
			}
		} else {
			followButton.setText(getString(R.string.user_main_follow));
		}
		followerCount.setText(getFollowerCountStr());
		followingCount.setText(getFollowingCountStr());
	}

	private String getIntroStr() {
		return (user.getIntro() == null) ? getString(R.string.user_main_not_found_intro) : user.getIntro();
	}	

	private String getFollowerCountStr() {
		return getString(R.string.user_main_follower) + ": " + user.getFollowerCount();
	}	

	private String getFollowingCountStr() {
		return getString(R.string.user_main_following) + ": " + user.getFollowingCount();
	}

	private void followRequest() {
		if (request == null || !(request instanceof FollowingHttpRequest)) {
			request = new FollowingHttpRequest(this);
		}

		((FollowingHttpRequest) request).actionNew(user.getId());
		manager.request(this, request, FOLLOW_REQUEST);
		user.setFollowerCount(user.getFollowerCount() + 1);
	}

	private void unfollowRequest() {
		if (request == null || !(request instanceof FollowingHttpRequest)) {
			request = new FollowingHttpRequest(this);
		}

		((FollowingHttpRequest) request).actionDelete(user.getId());
		manager.request(this, request, UN_FOLLOW_REQUEST);
		user.setFollowerCount(user.getFollowerCount() - 1);
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub
		setUserInfo();
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		// TODO Auto-generated method stub
		e.showToastMsg(getApplicationContext());
	}

	public void onFollowButtonClicked(View view) {
		if (session.isLogin()){
			if (session.getCurrentUser().getId() != user.getId()) {
				if (dbProvider.isExistFollowing(user.getId())) {
					dbProvider.deleteFollowing(user.getId());
					// api request
					unfollowRequest();
				}else {
					dbProvider.insertFollowing(user.getId());
					// api request
					followRequest();
				}
			}
		}
	}

	public void onBlogButtonClicked(View view) {

	}

	public void onJjimStoreButtonClicked(View view) {

	}

	public void onActivityAreaButtonClicked(View view) {
	}

	public void onMemoButtonClicked(View view) {
	}

	public void onImageButtonClicked(View view) {

	}

	public void onTagButtonClicked(View view) {

	}

	public void onUrlButtonClicked(View view) {

	}

}