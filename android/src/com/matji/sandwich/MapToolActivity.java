package com.matji.sandwich;

import android.os.Bundle;
import com.matji.sandwich.base.BaseMapActivity;

public class MapToolActivity extends BaseMapActivity {
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.main);
	}

	protected boolean isRouteDisplayed() {
		return true;
	}
}
