package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;

public class SettingAccountActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected String usedTitleBar() {
		return null;
	}
}
