package com.matji.sandwich;

import android.app.Activity;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;

public class TagActivity extends BaseActivity {
	@Override
	protected void onChildTitleChanged(Activity childActivity,
			CharSequence title) {
		// TODO Auto-generated method stub
		super.onChildTitleChanged(childActivity, title);

		TextView textview = new TextView(this);
		textview.setText("sadfasdfas");

		setContentView(textview);
	}

	@Override
	protected String usedTitleBar() {
		return null;
	}
}
