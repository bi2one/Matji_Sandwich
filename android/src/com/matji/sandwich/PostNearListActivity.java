package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.PostNearListView;

/**
 * 전체 Post 리스트를 보여주는 액티비티.
 * 
 * 
 * @author mozziluv
 *
 */
public class PostNearListActivity extends BaseActivity {
	private PostNearListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_near_list);

		listView = (PostNearListView) findViewById(R.id.post_near_list_view);
		listView.setActivity(this);
		listView.requestReload();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		listView.dataRefresh();
	}
}