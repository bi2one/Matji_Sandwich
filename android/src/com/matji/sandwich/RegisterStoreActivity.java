package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;

public class RegisterStoreActivity extends BaseActivity {
    public int setMainViewId() {
	return R.id.activity_register_store_list;
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
}
