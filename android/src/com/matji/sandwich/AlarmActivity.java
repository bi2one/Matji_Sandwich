package com.matji.sandwich;


import com.matji.sandwich.base.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.matji.sandwich.base.BaseActivity;

public class AlarmActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected String titleBarText() {
		// TODO Auto-generated method stub
		return "AlarmActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		
	}
}
