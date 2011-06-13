package com.matji.sandwich;

import com.matji.sandwich.session.Session;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends BaseActivity implements OnClickListener {
	private Button signin;
	private Button signout; 
	private Button signup;
	private TextView profile;
	private TextView notifications;
	private TextView messages;
	private TextView notices;
	private Session session;
	private User user;

	public void onCreate(Bundle savedInstanceState) {
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
		profile = (TextView) findViewById(R.id.profile);
		notifications = (TextView) findViewById(R.id.notifications);
		messages = (TextView) findViewById(R.id.messages);
		notices = (TextView) findViewById(R.id.notices);
		profile.setOnClickListener(this);
		notifications.setOnClickListener(this);
		messages.setOnClickListener(this);
		notices.setOnClickListener(this);
		
 		if (session.getToken() == null) {
			signin.setVisibility(Button.VISIBLE);
			signout.setVisibility(Button.GONE);
			signup.setVisibility(Button.VISIBLE);
			profile.setVisibility(TextView.GONE);
			notifications.setVisibility(TextView.GONE);
			messages.setVisibility(TextView.GONE);
		} else {
			signin.setVisibility(Button.GONE);
			signout.setVisibility(Button.VISIBLE);
			signup.setVisibility(Button.GONE);
			profile.setVisibility(TextView.VISIBLE);
			notifications.setVisibility(TextView.VISIBLE);
			messages.setVisibility(TextView.VISIBLE);
		}
	}

	protected void onPause() {
		super.onPause();
	}

	public void loginButtonClicked(View v) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivityForResult(intent, 1);
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

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.profile :
			Intent profileIntent = new Intent(this, UserTabActivity.class);
			session = Session.getInstance(this);
			user = session.getCurrentUser();
			profileIntent.putExtra("user", (Parcelable)user);
			startActivity(profileIntent);
			break;
		case R.id.notifications :
			Intent notificationIntent = new Intent(this, UserTabActivity.class);
			notificationIntent.putExtra("user", (Parcelable)user);
			startActivity(notificationIntent);
			break;
		case R.id.messages :
			Intent messageIntent = new Intent(this, UserTabActivity.class);
			messageIntent.putExtra("user", (Parcelable)user);
			startActivity(messageIntent);
			break;
		case R.id.notices :
			Intent noticesIntent = new Intent(this, NoticeActivity.class);
			startActivity(noticesIntent);
			break;
		}
	}
}
