package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.RoundTabHost;

public class StoreDetailInfoTabActivity extends BaseTabActivity {
	private RoundTabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tabHost = (RoundTabHost)getTabHost();
		

	}	
}