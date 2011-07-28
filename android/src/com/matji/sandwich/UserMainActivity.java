package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.FollowingActivity.FollowingListType;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.UserMileage;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.ModelType;
import com.matji.sandwich.widget.ProfileImageView;
import com.matji.sandwich.widget.RoundTabHost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//  User main x -> user talk activity
//public class UserMainActivity extends BaseTabActivity implements Requestable {
public class UserMainActivity extends BaseTabActivity {
	private RoundTabHost tabHost;
	private User user;
	private boolean me;

	private HttpRequestManager manager;
	private HttpRequest request;
	private Session session;
	private DBProvider dbProvider;

	/* request tags */
	private static final int FOLLOW_REQUEST = 10;
	private static final int UN_FOLLOW_REQUEST = 11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_main);

		tabHost = (RoundTabHost) getTabHost();
		manager = HttpRequestManager.getInstance(this);
		session = Session.getInstance(this);
		
		user = (User) SharedMatjiData.getInstance().top();
		dbProvider = DBProvider.getInstance(this);

		if (session.isLogin()) {
			me = (user.getId() == session.getCurrentUser().getId());
		}

		if (me) {
			// TODO
			SharedMatjiData.getInstance().pop();
			SharedMatjiData.getInstance().push(session.getCurrentUser());
			user = (User) SharedMatjiData.getInstance().top();
		}
		
		tabHost.addLeftTab("tab1",
				R.string.store_main_post_list_view,
				new Intent(this, UserPostListActivity.class));
		Intent imageViewIntent = new Intent(this, ImageListActivity.class);
		imageViewIntent.putExtra("id", user.getId());
		imageViewIntent.putExtra("type", ModelType.USER);
		tabHost.addCenterTab("tab2",
				R.string.store_main_img,
				imageViewIntent);

		tabHost.addRightTab("tab3",
				R.string.store_main_review,
				new Intent(this, UserTagActivity.class));
//		new Intent(this, UserStoreListActivity.class));

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
		
	}
	
	public void refresh() {
		
	}

//
//	private void followRequest() {
//		if (request == null || !(request instanceof FollowingHttpRequest)) {
//			request = new FollowingHttpRequest(this);
//		}
//
//		((FollowingHttpRequest) request).actionNew(user.getId());
//		manager.request(this, request, FOLLOW_REQUEST, this);
//		user.setFollowerCount(user.getFollowerCount() + 1);
//		User me = session.getCurrentUser();
//		me.setFollowingCount(me.getFollowingCount() + 1);
//	}
//
//	private void unfollowRequest() {
//		if (request == null || !(request instanceof FollowingHttpRequest)) {
//			request = new FollowingHttpRequest(this);
//		}
//
//		((FollowingHttpRequest) request).actionDelete(user.getId());
//		manager.request(this, request, UN_FOLLOW_REQUEST, this);
//		user.setFollowerCount(user.getFollowerCount() - 1);
//		User me = session.getCurrentUser();
//		me.setFollowingCount(me.getFollowingCount() - 1);
//	}
//
//	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
//		switch (tag) {
//		case FOLLOW_REQUEST:
//			dbProvider.insertFollowing(user.getId());
//			break;
//		case UN_FOLLOW_REQUEST:
//			dbProvider.deleteFollowing(user.getId());
//			break;
//		}
//
//		followButton.setClickable(true);
//		refresh();
//	}
//
//	public void requestExceptionCallBack(int tag, MatjiException e) {
//		e.showToastMsg(getApplicationContext());
//	}
//
//	public void onFollowButtonClicked(View view) {
//		if (loginRequired()) {
//			if (!manager.isRunning(this)) {
//				if (session.getCurrentUser().getId() != user.getId()) {
//					followButton.setClickable(false);
//					if (dbProvider.isExistFollowing(user.getId())) {
//						// api request
//						unfollowRequest();
//					}else {
//						// api request
//						followRequest();
//					}
//				}
//			}
//		}
//	}
//
//	public void onBlogButtonClicked(View view) {
//	}
//
//	public void onFollowerButtonClicked(View view) {
//		Intent intent = new Intent(this, FollowingActivity.class);
//		intent.putExtra("user_id", user.getId());
//		intent.putExtra("type", FollowingListType.FOLLOWER);
//
//		startActivity(intent);
//	}
//
//	public void onFollowingButtonClicked(View view) {
//		Intent intent = new Intent(this, FollowingActivity.class);
//		intent.putExtra("user_id", user.getId());
//		intent.putExtra("type", FollowingListType.FOLLOWING);
//
//		startActivity(intent);
//	}

//	public void onJjimStoreButtonClicked(View view) {
//		tabHost.setCurrentTab(UserTabActivity.JJIM_STORE_PAGE);
//	}
//
//	public void onMemoButtonClicked(View view) {
//		tabHost.setCurrentTab(UserTabActivity.MEMO_PAGE);
//	}
//
//	public void onTagButtonClicked(View view) {
//		Intent intent = new Intent(this, TagListActivity.class);
//		intent.putExtra("id", user.getId());
//		intent.putExtra("type", ModelType.USER);
//		startActivity(intent);
//	}
//
//	public void onImageButtonClicked(View view) {
//		tabHost.setCurrentTab(UserTabActivity.IMAGE_PAGE);
//	}
//
//	private void onMessageButtonClicked() {
//		if (loginRequired()) {
//			Intent intent = new Intent(this, WriteMessageActivity.class);
//			intent.putExtra("user_id", user.getId());
//			startActivity(intent);
//		}
//	}
//	

	

	public String getCount(int id, int count) {
		return getString(id) + ": " + count;
	}
	
	public String getCountNumberOf(int id, int count) {
		return getString(id) + ": " + count + getString(R.string.default_string_number_of);
	}
}