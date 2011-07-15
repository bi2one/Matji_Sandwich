package com.matji.sandwich;

import android.app.Activity;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.title.TitleText;

public class StoreMoreActivity extends BaseActivity {
	@Override
	protected void onChildTitleChanged(Activity childActivity,
			CharSequence title) {
		// TODO Auto-generated method stub
		super.onChildTitleChanged(childActivity, title);
		setContentView(R.layout.main);
	}

	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "StoreMoreActivity");
	}
}
