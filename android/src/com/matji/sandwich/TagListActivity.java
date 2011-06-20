package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.util.ModelType;
import com.matji.sandwich.widget.TagListView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TagListActivity extends BaseActivity {
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);

		int id = getIntent().getIntExtra("id", 0);
		ModelType type = (ModelType) getIntent().getSerializableExtra("type");
		TagListView listView = (TagListView) findViewById(R.id.tag_list);
		listView.setId(id);
		listView.setModelType(type);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected String titleBarText() {
		return "StoreTagListActivity";
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