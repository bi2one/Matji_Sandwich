package com.matji.sandwich;

import android.app.Activity;

import com.matji.sandwich.base.BaseActivity;

public class UrlActivity extends BaseActivity {
	@Override
	protected void onChildTitleChanged(Activity childActivity,
			CharSequence title) {
		// TODO Auto-generated method stub
		super.onChildTitleChanged(childActivity, title);
		setContentView(R.layout.main);
	}
	
	@Override
	protected String usedTitleBar() {
		return null;
	}
}
