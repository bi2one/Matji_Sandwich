package com.matji.sandwich;

import java.util.ArrayList;

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
import com.matji.sandwich.widget.title.TitleButton;
import com.matji.sandwich.widget.title.TitleText;

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
	private User user;
	private boolean me;

	private HttpRequestManager manager;
	private HttpRequest request;
	private Session session;
	private DBProvider dbProvider;

	private TextView gradeText;
	private TextView pointText1;
	private TextView pointText2;
	private TextView titleText;
	private TextView introText;
	private Button followButton;
	private TextView followingYouText;
	private TextView blogText;
	private TextView ownerText;

	private Button followerButton;
	private Button followingButton;
	private Button jjimStoreButton;
	private Button memoButton;
	private Button tagButton;
	private Button imageButton;

	/* request tags */
	private static final int FOLLOW_REQUEST = 1;
	private static final int UN_FOLLOW_REQUEST = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_main);

		tabHost = ((TabActivity) getParent()).getTabHost();
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

		gradeText = (TextView) findViewById(R.id.user_cell_grade);
		pointText1 = (TextView) findViewById(R.id.user_cell_point_text);
		pointText2 = (TextView) findViewById(R.id.user_cell_point);
		titleText = (TextView) findViewById(R.id.user_cell_title);
		introText = (TextView) findViewById(R.id.user_cell_intro);
		followButton = (Button) findViewById(R.id.user_main_follow_btn);
		followingYouText = (TextView) findViewById(R.id.user_main_following_you);
		blogText = (TextView) findViewById(R.id.user_main_blog);
		ownerText = (TextView) findViewById(R.id.user_main_owner);

		followerButton = (Button) findViewById(R.id.user_main_follower_btn);
		followingButton = (Button) findViewById(R.id.user_main_following_btn);
		jjimStoreButton = (Button) findViewById(R.id.user_main_jjim_store_btn);
		memoButton = (Button) findViewById(R.id.user_main_memo_btn);
		tagButton = (Button) findViewById(R.id.user_main_tag_btn);
		imageButton = (Button) findViewById(R.id.user_main_image_btn);		
	}

	private void setInfo() {
		/* Set Grade */
		UserMileage mileage = user.getMileage();
		String grade = "E";
		int totalPoint = 0;

		if (mileage != null) {
			grade = mileage.getGrade();
			totalPoint = mileage.getTotalPoint();
		}

		if (grade.equals("E")) {
			gradeText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			gradeText.setText(getString(R.string.grade_diamond) + "E");
		} else if (grade.equals("D")) {
			gradeText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			gradeText.setText(getString(R.string.grade_diamond) + "D");
		} else if (grade.equals("C")) {
			gradeText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			gradeText.setText(getString(R.string.grade_diamond) + "C");
		} else if (grade.equals("B")) {
			gradeText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			gradeText.setText(getString(R.string.grade_diamond) + "B");
		} else if (grade.equals("A")) {
			gradeText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			gradeText.setText(getString(R.string.grade_diamond) + "A");
		}

		pointText1.setText(R.string.grade_point);
		pointText2.setText(" " + totalPoint);

		if (dbProvider.isExistFollower(user.getId())) {
			followingYouText.setText(user.getNick() + " " + getString(R.string.user_main_following_you));
			followingYouText.setVisibility(TextView.VISIBLE);
		} else {
			followingYouText.setVisibility(TextView.GONE);
		}

		/* Set User Image */
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SMALL, (ImageView) findViewById(R.id.user_cell_thumnail));

		if (session.isLogin()) {
			if (dbProvider.isExistFollowing(user.getId())) {
				followButton.setText(R.string.user_main_unfollow);
			} else {
				followButton.setText(R.string.user_main_follow);
			}
		} else {
			followButton.setText(R.string.user_main_follow);
		}

		titleText.setText(user.getTitle());
		introText.setText(user.getIntro());

		blogText.setText(user.getNick() + getString(R.string.user_main_not_exist_blog));

		followerButton.setText(getCount(R.string.user_main_follower, user.getFollowerCount()));
		followingButton.setText(getCount(R.string.user_main_following, user.getFollowingCount()));
		jjimStoreButton.setText(getCountNumberOf(R.string.user_main_jjim_store, user.getStoreCount()));
		memoButton.setText(getCountNumberOf(R.string.default_string_memo, user.getPostCount()));
		tagButton.setText(getCountNumberOf(R.string.default_string_tag, user.getTagCount()));
		imageButton.setText(getCountNumberOf(R.string.default_string_image, user.getImageCount()));
		//TODO
		//		imageButton.setText(getCountNumberOf(R.string.default_string_image, user.get));
	}

	@Override
	public void onResume() {
		super.onResume();

		setInfo();
	}


	private void followRequest() {
		if (request == null || !(request instanceof FollowingHttpRequest)) {
			request = new FollowingHttpRequest(this);
		}

		((FollowingHttpRequest) request).actionNew(user.getId());
		manager.request(this, request, FOLLOW_REQUEST, this);
		user.setFollowerCount(user.getFollowerCount() + 1);
		User me = session.getCurrentUser();
		me.setFollowingCount(me.getFollowingCount() + 1);
	}

	private void unfollowRequest() {
		if (request == null || !(request instanceof FollowingHttpRequest)) {
			request = new FollowingHttpRequest(this);
		}

		((FollowingHttpRequest) request).actionDelete(user.getId());
		manager.request(this, request, UN_FOLLOW_REQUEST, this);
		user.setFollowerCount(user.getFollowerCount() - 1);
		User me = session.getCurrentUser();
		me.setFollowingCount(me.getFollowingCount() - 1);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case FOLLOW_REQUEST:
			dbProvider.insertFollowing(user.getId());
			break;
		case UN_FOLLOW_REQUEST:
			dbProvider.deleteFollowing(user.getId());
			break;
		}

		followButton.setClickable(true);
		setInfo();
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}

	public void onFollowButtonClicked(View view) {
		if (loginRequired()) {
			if (!manager.isRunning(this)) {
				if (session.getCurrentUser().getId() != user.getId()) {
					followButton.setClickable(false);
					if (dbProvider.isExistFollowing(user.getId())) {
						// api request
						unfollowRequest();
					}else {
						// api request
						followRequest();
					}
				}
			}
		}
	}

	public void onBlogButtonClicked(View view) {
	}

	public void onFollowerButtonClicked(View view) {
		Intent intent = new Intent(this, FollowingActivity.class);
		intent.putExtra("user_id", user.getId());
		intent.putExtra("type", FollowingListType.FOLLOWER);

		startActivity(intent);
	}

	public void onFollowingButtonClicked(View view) {
		Intent intent = new Intent(this, FollowingActivity.class);
		intent.putExtra("user_id", user.getId());
		intent.putExtra("type", FollowingListType.FOLLOWING);

		startActivity(intent);
	}

	public void onJjimStoreButtonClicked(View view) {
		tabHost.setCurrentTab(UserTabActivity.JJIM_STORE_PAGE);
	}

	public void onMemoButtonClicked(View view) {
		tabHost.setCurrentTab(UserTabActivity.MEMO_PAGE);
	}

	public void onTagButtonClicked(View view) {
		Intent intent = new Intent(this, TagListActivity.class);
		intent.putExtra("id", user.getId());
		intent.putExtra("type", ModelType.USER);
		startActivity(intent);
	}

	public void onImageButtonClicked(View view) {
		tabHost.setCurrentTab(UserTabActivity.IMAGE_PAGE);
	}

	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "UserMainActivity");
	}

	@Override
	protected View setRightTitleView() {
		if (session.isLogin() && session.getCurrentUser().getId() != user.getId()) {
			return new TitleButton(this, "Message") {
				@Override
				public void onClick(View arg0) {
					onMessageButtonClicked();
				}
			};
		} else {
			return super.setRightTitleView();
		}
	}

	private void onMessageButtonClicked() {
		if (loginRequired()) {
			Intent intent = new Intent(this, WriteMessageActivity.class);
			intent.putExtra("user_id", user.getId());
			startActivity(intent);
		}
	}
}