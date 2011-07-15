package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.ModelType;
import com.matji.sandwich.widget.ImageListView;
import com.matji.sandwich.widget.title.TitleText;

import android.os.Bundle;
import android.view.View;

public class ImageListActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		int id = getIntent().getIntExtra("id", 0);
		ModelType type = (ModelType) getIntent().getSerializableExtra("type");
		ImageListView listView = (ImageListView) findViewById(R.id.image_list_view);
		listView.setModelId(id);
		listView.setType(type);
		listView.setActivity(this);
		listView.requestReload();
	}
	
	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "ImageListActivity");
	}
}