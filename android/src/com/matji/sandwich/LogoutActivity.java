package com.matji.sandwich;

import android.os.Bundle;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.title.TitleText;

public class LogoutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "LogoutActivity");
	}
}
