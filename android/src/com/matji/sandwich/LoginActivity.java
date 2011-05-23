package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class LoginActivity extends Activity implements OnClickListener {
	private Button okButton;
	private Button cancelButton;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		okButton = (Button)findViewById(R.id.ok);
		cancelButton = (Button)findViewById(R.id.cancel);
		
		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);		
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.ok:
			
		case R.id.cancel:
			finish();
			break;
		}
	}
}
