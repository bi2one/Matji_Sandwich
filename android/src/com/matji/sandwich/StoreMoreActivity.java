package com.matji.sandwich;

import android.app.Activity;

import com.matji.sandwich.base.BaseActivity;

public class StoreMoreActivity extends BaseActivity {
    public int setMainViewId() {
	return R.id.activity_main;
    }
    
	@Override
	protected void onChildTitleChanged(Activity childActivity,
			CharSequence title) {
		// TODO Auto-generated method stub
		super.onChildTitleChanged(childActivity, title);
		setContentView(R.layout.main);
	}
}
