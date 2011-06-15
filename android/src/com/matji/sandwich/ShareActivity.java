package com.matji.sandwich;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.matji.sandwich.base.BaseActivity;

public class ShareActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected String titleBarText() {
		return "ShareActivity";
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
