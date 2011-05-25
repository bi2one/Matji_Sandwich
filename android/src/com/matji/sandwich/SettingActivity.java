package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SettingActivity extends Activity implements OnClickListener {
	private Button signin;
	LinearLayout layout;
	Button btn;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		signin = (Button) findViewById(R.id.signin);
		signin.setTag("SignIn");
		signin.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v.getTag().toString().equals("SignIn")) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivityForResult(intent, 1);
		} else
			layout.removeView(v);
	}
}
