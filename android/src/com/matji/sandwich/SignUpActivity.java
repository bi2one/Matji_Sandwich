package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends BaseActivity {
    public int setMainViewId() {
	return R.id.activity_signup;
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
	}
	
	public void signupButtonClicked(View v){
		EditText nameField = (EditText) findViewById(R.id.name);
		EditText emailField = (EditText) findViewById(R.id.email);
		EditText usernameField = (EditText) findViewById(R.id.username);
		EditText passwordField = (EditText) findViewById(R.id.password);
		
	}
	
	public void cancelButtonClicked(View v) {
		finish();
	}
}
