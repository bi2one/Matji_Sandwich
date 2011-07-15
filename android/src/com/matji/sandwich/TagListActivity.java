package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.ModelType;
import com.matji.sandwich.widget.TagListView;
import com.matji.sandwich.widget.title.TitleText;

import android.os.Bundle;
import android.view.View;

public class TagListActivity extends BaseActivity {
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);

		int id = getIntent().getIntExtra("id", 0);
		ModelType type = (ModelType) getIntent().getSerializableExtra("type");
		TagListView listView = (TagListView) findViewById(R.id.tag_list);
		listView.setModelId(id);
		listView.setModelType(type);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "StoreTagListActivity");
	}
}