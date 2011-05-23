package com.matji.sandwich;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewGroup.*;
import android.widget.*;

public class SettingActivity extends Activity implements OnClickListener {
	private int i = 0;
	private Button signin;
	LinearLayout layout;
	Button btn;

	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.settings);

	signin = (Button)findViewById(R.id.signin);
	signin.setTag("SignIn");
	signin.setOnClickListener(this);
//	layout = (LinearLayout)findViewById(R.id.setting_layout);
//	
//	LinearLayout.LayoutParams linearParam;
//	linearParam = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//	
//	layout.setOrientation(LinearLayout.VERTICAL);
//	
//	btn = new Button(this);
//	
//	btn.setText("SignIn");
//	btn.setTag("SignIn");
//	btn.setInputType(InputType.TYPE_CLASS_NUMBER);
//	btn.setOnClickListener(this);
//	layout.addView(btn, linearParam);
    }
	
//	private void setButton(View v) {
//		btn = new Button(this);
//		btn.setText("delete");
//		btn.setTag("delete");
//		btn.setOnClickListener(this);
//		
//		LinearLayout layout = (LinearLayout)findViewById(R.id.setting_layout);
//		LinearLayout.LayoutParams linearParam;
//		linearParam = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		layout.setOrientation(LinearLayout.VERTICAL);
//		layout.addView(btn, linearParam);
//		
//		btn = new Button(this);
//
//		btn.setText("add");
//		btn.setTag("add");
//		btn.setInputType(InputType.TYPE_CLASS_NUMBER);
//		btn.setOnClickListener(this);
//		
//		layout = (LinearLayout)findViewById(R.id.setting_layout);
//		linearParam = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		layout.setOrientation(LinearLayout.VERTICAL);
//		layout.addView(btn, linearParam);
//	}

	public void onClick(View v) {
		if(v.getTag().toString().equals("SignIn")) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else layout.removeView(v);
	}
}
