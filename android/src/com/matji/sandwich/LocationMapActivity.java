package com.matji.sandwich;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.matji.sandwich.base.BaseMapActivity;

public class LocationMapActivity extends BaseMapActivity {
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.main);
	}

	protected boolean isRouteDisplayed() {
		return true;
	}

	@Override
	protected String titleBarText() {
		return "LocationMapActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		
	}
}
