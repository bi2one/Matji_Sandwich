package com.matji.sandwich;

import com.matji.sandwich.session.Session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;

public class LoginActivity extends Activity implements Loginable {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

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
		setResult(RESULT_OK);
		finish();
	}

	public void loginFailed() {
		// show toast -> id, pw 확인해라
	}

}
