package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.SectionedPostListView;

/**
 * 전체 Post 리스트를 보여주는 액티비티.
 * 
 * 
 * @author mozziluv
 *
 */
public class PostListActivity extends BaseActivity {
	private SectionedPostListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_list);

		listView = (SectionedPostListView) findViewById(R.id.post_list_view);
		listView.setActivity(this);
		listView.requestReload();
	}
	@Override
	protected void onResume() {
		super.onResume();
		listView.dataRefresh();
	}
}