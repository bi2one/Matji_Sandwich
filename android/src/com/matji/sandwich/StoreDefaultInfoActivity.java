package com.matji.sandwich;

import android.os.Bundle;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;

public class StoreDefaultInfoActivity extends BaseActivity {
	private Store store;
	private TextView fullName;
	private TextView cover;
	private TextView tel;
	private TextView address;
	private TextView website;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_default_info);

		store = (Store) SharedMatjiData.getInstance().top();

		fullName = (TextView) findViewById(R.id.store_info_fullname);
		cover = (TextView) findViewById(R.id.store_info_cover);
		tel = (TextView) findViewById(R.id.store_info_tel);
		address = (TextView) findViewById(R.id.store_info_address);
		website = (TextView) findViewById(R.id.store_info_website);
		
		fullName.setText(store.getName());
		tel.setText(store.getTel());
		address.setText(store.getAddress());
		
		if(store.getCover() != null)
			cover.setText(store.getCover());
		else	
			cover.setText("-");
		
		if(store.getWebsite() != null)
			website.setText(store.getWebsite());
		else
			website.setText("-");
	}
}