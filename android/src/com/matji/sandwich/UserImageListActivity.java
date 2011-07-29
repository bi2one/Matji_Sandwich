package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.UserImageListView;

import android.os.Bundle;

public class UserImageListActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_image_list);

		UserImageListView listView = (UserImageListView) findViewById(R.id.user_image_list_view);
		listView.setActivity(this);
		listView.requestReload();
	}
}