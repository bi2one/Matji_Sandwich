package com.matji.sandwich;

import android.os.Bundle;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;

public class StoreDefaultInfoActivity extends BaseActivity {
	private Store store;
	private TextView defaultInfo;
	public static final String STORE  = "store";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init() {
		super.init();
		setContentView(R.layout.activity_store_default_info);
		store = (Store) getIntent().getParcelableExtra(STORE);
	}
}