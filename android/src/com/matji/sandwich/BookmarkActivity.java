package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;

import android.os.Bundle;

public class BookmarkActivity extends BaseActivity {
    public int setMainViewId() {
	return R.id.activity_main;
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
}
