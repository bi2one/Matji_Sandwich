package com.matji.sandwich;


import com.matji.sandwich.base.BaseActivity;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;

public class AlarmActivity extends BaseActivity {
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
