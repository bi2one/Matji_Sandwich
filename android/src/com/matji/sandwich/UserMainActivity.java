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

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class UserMainActivity extends MainActivity implements Requestable {
	private TabHost tabHost;
	private Intent intent;
	private User user;
	private MatjiImageDownloader downloader;

	private HttpRequestManager manager;
	private HttpRequest request;
	private Session session;
	private DBProvider dbProvider;

	private TextView gradeText;
	private TextView titleText;
	private TextView introText;
	private Button followButton;
	private TextView followMessageText;
	private TextView followerCountText;
	private TextView followingCountText;
	
	private Button followerButton;
	private Button followingButton;
	
	private Button jjimStoreButton;
//	private Button activityAreaButton;
	private Button memoButton;
//	private Button imageButton;
//	private Button tagButton;
//	private Button urlButton;
//	private Button sentMessageButton;
//	private Button recievedMessageButton;

	private static final int FOLLOW_REQUEST = 1;
	private static final int UN_FOLLOW_REQUEST = 2;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_main);

		initInfo();
	}

	public void onResume() {
		super.onResume();
		followButton.setClickable(true);		
	}
	
	public void initInfo() {
		tabHost = ((TabActivity) getParent()).getTabHost();
		intent = getIntent();
		user = intent.getParcelableExtra("user");
		downloader = new MatjiImageDownloader();

		manager = new HttpRequestManager(this, this);
		session = Session.getInstance(this);
		dbProvider = DBProvider.getInstance(this);		
		
		gradeText = (TextView) findViewById(R.id.user_cell_grade);
		titleText = (TextView) findViewById(R.id.user_cell_title);
		introText = (TextView) findViewById(R.id.user_cell_intro);
		followButton = (Button) findViewById(R.id.user_main_follow_btn);
		followMessageText = (TextView) findViewById(R.id.user_main_follow_message);
		followerCountText = (TextView) findViewById(R.id.user_main_follower_count);
		followingCountText = (TextView) findViewById(R.id.user_main_following_count);
		
		followerButton = (Button) findViewById(R.id.user_main_follower_btn);
		followingButton = (Button) findViewById(R.id.user_main_following_btn);
		jjimStoreButton = (Button) findViewById(R.id.user_main_jjim_store_btn);
//		activityAreaButton = (Button) findViewById(R.id.user_main_activity_area_btn);
		memoButton = (Button) findViewById(R.id.user_main_memo_btn);
//		imageButton = (Button) findViewById(R.id.user_main_image_btn);
//		tagButton = (Button) findViewById(R.id.user_main_tag_btn);
//		urlButton = (Button) findViewById(R.id.user_main_url_btn);
//		sentMessageButton = (Button) findViewById(R.id.user_main_sent_message_btn);
//		recievedMessageButton = (Button) findViewById(R.id.user_main_recieved_message_btn);

		/* Set User Image */
		downloader.downloadUserImage(user.getId(), (ImageView) findViewById(R.id.user_cell_image));
		titleText.setText(user.getTitle());

		/* Set Intro */
		String intro = (user.getIntro() == null) ? getString(R.string.user_main_not_found_intro) : user.getIntro();
		introText.setText(intro);

		setInfo();
	}

	public void setInfo() {
		/* Set Grade */
		gradeText.setText("다이아몬드"); // TODO		

		if (session.isLogin()) {
			if (dbProvider.isExistFollowing(user.getId())) {
				followButton.setText(getString(R.string.user_main_unfollow));
			} else {
				followButton.setText(getString(R.string.user_main_follow));
			}
		} else {
			followButton.setText(getString(R.string.user_main_follow));
		}
		followerCountText.setText(getCount(R.string.user_main_follower, user.getFollowerCount()));
		followingCountText.setText(getCount(R.string.user_main_following, user.getFollowingCount()));
		followerButton.setText(getCount(R.string.user_main_follower, user.getFollowerCount()));
		followingButton.setText(getCount(R.string.user_main_following, user.getFollowingCount()));
		jjimStoreButton.setText(getCountNumberOf(R.string.user_main_jjim_store, user.getStoreCount()));
//		activityAreaButton.setText(getString(R.string.user_main_activity_area) + ": 한국");
		memoButton.setText(getCountNumberOf(R.string.default_string_memo, user.getPostCount()));
//		imageButton.setText(getCountNumberOf(R.string.default_string_image, user.getAttachFiles().size()));
//		tagButton.setText(getCountNumberOf(R.string.default_string_tag, user.getTagCount()));
//		urlButton.setText(getCountNumberOf(R.string.default_string_url, 0));
//		sentMessageButton.setText(getCountNumberOf(R.string.default_string_sent_message, 0));
//		recievedMessageButton.setText(getCountNumberOf(R.string.default_string_recieved_message,0));
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
		setInfo();
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		// TODO Auto-generated method stub
		e.showToastMsg(getApplicationContext());
	}

	public void onFollowButtonClicked(View view) {
		followButton.setClickable(false);
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

	public void onFollowerButtonClicked(View view) {
		
	}

	public void onFollowingButtonClicked(View view) {
		
	}
		
	public void onJjimStoreButtonClicked(View view) {
	}

//	public void onActivityAreaButtonClicked(View view) {
//	}

	public void onMemoButtonClicked(View view) {
		tabHost.setCurrentTab(UserTabActivity.MEMO_PAGE);
	}

//	public void onImageButtonClicked(View view) {
//
//	}
//
//	public void onTagButtonClicked(View view) {
//
//	}
//
//	public void onUrlButtonClicked(View view) {
//
//	}
}