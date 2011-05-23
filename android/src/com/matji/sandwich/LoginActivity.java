package com.matji.sandwich;

import com.matji.sandwich.session.Session;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText usernameField;
	private EditText passwordField;
	private Button okButton;
	private Button cancelButton;
	 	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		usernameField = (EditText)findViewById(R.id.username);
		passwordField = (EditText)findViewById(R.id.password);
		okButton = (Button)findViewById(R.id.ok);
		cancelButton = (Button)findViewById(R.id.cancel);
		
		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);		
	}

	public void onClick(View v) {
		Session session = Session.getInstance(this);
		switch(v.getId()) {
		case R.id.ok:
			session.login(this, usernameField.getText().toString(), passwordField.getText().toString());
			break;
		case R.id.cancel:
			finish();
			break;
		}
	}
}
