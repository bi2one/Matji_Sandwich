package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.SectionListView;

public class PostListActivity extends BaseActivity {
	private SectionListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_list);

		listView = (SectionListView) findViewById(R.id.post_list_view);
		listView.setActivity(this);
		listView.requestReload();
	}
	@Override
	protected void onResume() {
		super.onResume();
		listView.dataRefresh();
	}
}