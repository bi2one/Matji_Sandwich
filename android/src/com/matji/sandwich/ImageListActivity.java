package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.util.ModelType;
import com.matji.sandwich.widget.ImageListView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
	protected String titleBarText() {
		return "ImageListActivity";
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