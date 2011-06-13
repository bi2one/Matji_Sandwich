package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.Session;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements Loginable {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

	}

	public void loginButtonClicked(View v) {
		EditText usernameField = (EditText) findViewById(R.id.username);
		EditText passwordField = (EditText) findViewById(R.id.password);
		Session session = Session.getInstance(this);
		session.login(this, usernameField.getText().toString(), passwordField.getText().toString());
	}

	public void cancelButtonClicked(View v) {
		finish();

	}

	/* Loginable Interface methods */
	public void loginCompleted() {
		this.setResult(RESULT_OK);
		finish();
	}

	public void loginFailed() {
		// show toast -> id, pw 확인해라
	}

}
