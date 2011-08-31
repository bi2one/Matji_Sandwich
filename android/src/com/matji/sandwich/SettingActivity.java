package com.matji.sandwich;

import com.matji.sandwich.session.Session;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingActivity extends BaseActivity {
	private Button signin;
	private Button signout; 
	private Button signup;
	private Session session;
	private User user;
	private MatjiImageDownloader downloader;

    public int setMainViewId() {
	return R.id.setting_layout;
    }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	protected void onResume() {
		super.onResume();
		configureViews();
	}

	private void configureViews() {
		session = Session.getInstance(this);
		signin = (Button) findViewById(R.id.signin);
		signout = (Button) findViewById(R.id.signout);
		signup = (Button) findViewById(R.id.signup);
		
//		TextView profile = (TextView) findViewById(R.id.profile);
//		TextView notifications = (TextView) findViewById(R.id.notifications);
//		TextView messages = (TextView) findViewById(R.id.messages);
//		TextView notices = (TextView) findViewById(R.id.notices);
		LinearLayout ll = (LinearLayout)findViewById(R.id.user_settings);
		ImageView myPageImage = (ImageView) findViewById(R.id.mypage_img);
		TextView myPage = (TextView) findViewById(R.id.mypage_txt); 
		
 		if (session.getToken() == null) {
			signin.setVisibility(Button.VISIBLE);
			signout.setVisibility(Button.GONE);
			signup.setVisibility(Button.VISIBLE);
			ll.setVisibility(View.GONE);
//			
//			profile.setVisibility(TextView.GONE);
//			notifications.setVisibility(TextView.GONE);
//			messages.setVisibility(TextView.GONE);
			
		} else {
			user = session.getCurrentUser();
			downloader = new MatjiImageDownloader(this);
			downloader.downloadUserImage(user.getId(), myPageImage);
			myPage.setText(user.getNick());

			signin.setVisibility(Button.GONE);
			signout.setVisibility(Button.VISIBLE);
			signup.setVisibility(Button.GONE);
			ll.setVisibility(View.VISIBLE);
//			
//			profile.setVisibility(TextView.VISIBLE);
//			notifications.setVisibility(TextView.VISIBLE);
//			messages.setVisibility(TextView.VISIBLE);
		}
	}

	protected void onPause() {
		super.onPause();
	}

	public void loginButtonClicked(View v) {
//		Intent intent = new Intent(this, LoginActivity.class);
//		startActivityForResult(intent, 1);
	}

	public void logoutButtonClicked(View v) {
		session = Session.getInstance(this);
		session.logout();
		configureViews();
	}

	public void signupButtonClicked(View v) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivityForResult(intent, 1);
	}

	public void onMyPageButtonClicked(View view) {
		Intent profileIntent = new Intent(this, UserTabActivity.class);
		profileIntent.putExtra(UserTabActivity.USER, (Parcelable) user);
		session = Session.getInstance(this);
		user = session.getCurrentUser();
		startActivity(profileIntent);
	}
	
	public void onProfileButtonClicked(View view){
	}
	
	public void onNotificationsButtonClicked(View view){
		Intent notificationIntent = new Intent(this, AlarmActivity.class);
//		notificationIntent.putExtra(AlarmActivity.USER, (Parcelable) user);
//		user = session.getCurrentUser();
		startActivity(notificationIntent);
	}
	
	public void onMessagesButtonClicked(View view){
//		Intent messageIntent = new Intent(this, MessageThreadActivity.class);
//		startActivity(messageIntent);
//		user = session.getCurrentUser();
//		startActivityWithMatjiData(messageIntent, user);
	}
	
	public void onNoticesButtonClicked(View view){
		Intent noticesIntent = new Intent(this, NoticeActivity.class);
		startActivity(noticesIntent);
	}
	
	public void onSyncButtonClicked(View view){
		Log.d("Button", "Sync Clicked");
	}
	
	public void onAboutButtonClicked(View view){
		Log.d("Button", "About Clicked");
	}
	
	public void onManualButtonClicked(View view){
		Log.d("Button", "Manual Clicked");
	}

	public void onVersionButtonClicked(View view){
		Log.d("Button", "Version Clicked");
	}
}
