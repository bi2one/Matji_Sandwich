package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BookmarkActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected String titleBarText() {
		// TODO Auto-generated method stub
		return "BookmarkActivity";
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
