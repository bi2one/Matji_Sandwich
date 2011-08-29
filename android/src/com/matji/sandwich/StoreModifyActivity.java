package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;

public class StoreModifyActivity extends BaseActivity {
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
