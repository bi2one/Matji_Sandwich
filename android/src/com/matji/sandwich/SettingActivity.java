package com.matji.sandwich;

import com.matji.sandwich.session.*;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SettingActivity extends Activity { 
	LinearLayout layout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);		
	}
	
	protected void onResume(){
		super.onResume();
		configureViews();
	}
	
	
	private void configureViews(){
		Session session = Session.getInstance(this);
		
		Button signin = (Button) findViewById(R.id.signin);
		Button signout = (Button) findViewById(R.id.signout);
		Button signup = (Button) findViewById(R.id.signup);
		
		if (session.getToken() == null){
			Log.d("=====", "No token");
			signin.setVisibility(Button.VISIBLE);
			signout.setVisibility(Button.GONE);
			signup.setVisibility(Button.VISIBLE);
		}else {
			Log.d("=====", "token");
			signin.setVisibility(Button.GONE);
			signout.setVisibility(Button.VISIBLE);
			signup.setVisibility(Button.GONE);
		}
	}
	
	
	
	protected void onPause(){
		super.onPause();
	}
	
	public void loginButtonClicked(View v){

		Intent intent = new Intent(this, LoginActivity.class);
		startActivityForResult(intent, 1);
	}
	
	public void logoutButtonClicked(View v){
		Session session = Session.getInstance(this);
		session.logout();
		
		configureViews();
	}
	
	public void signupButtonClicked(View v){

		Intent intent = new Intent(this, SignUpActivity.class);
		startActivityForResult(intent, 1);
	}
	
	
}
