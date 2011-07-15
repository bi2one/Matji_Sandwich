package com.matji.sandwich;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.title.TitleText;

public class StoreDefaultInfoActivity extends BaseActivity {
	private Store store;
	private TextView name;
	private TextView address;
	private TextView tel;
	private TextView website;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_default_info);

		store = (Store) SharedMatjiData.getInstance().top();
		name = (TextView) findViewById(R.id.store_default_info_name);
		address = (TextView) findViewById(R.id.store_default_info_address);
		tel = (TextView) findViewById(R.id.store_default_info_phone);
		website = (TextView) findViewById(R.id.store_default_info_web);
		
		name.setText(store.getName());
		address.setText(store.getAddress());
		tel.setText(store.getTel());
		website.setText(store.getWebsite());
	}

	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "StoreDefaultInfoActivity");
	}
}