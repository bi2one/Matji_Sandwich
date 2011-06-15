package com.matji.sandwich;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.matji.sandwich.base.BaseActivity;

public class StoreMoreActivity extends BaseActivity {
	@Override
	protected void onChildTitleChanged(Activity childActivity,
			CharSequence title) {
		// TODO Auto-generated method stub
		super.onChildTitleChanged(childActivity, title);
		setContentView(R.layout.main);
	}

	@Override
	protected String titleBarText() {
		return "StoreMoreActivity";
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
