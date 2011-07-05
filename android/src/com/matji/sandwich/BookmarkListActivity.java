package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.BookmarkListView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class BookmarkListActivity extends BaseActivity {
    private BookmarkListView listView;
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_bookmark_list);

	listView = (BookmarkListView) findViewById(R.id.bookmark_list);
	listView.setActivity(this);
	listView.requestReload();
    }
	
    protected void onResume() {
	super.onResume();
	listView.dataRefresh();
    }

    protected String titleBarText() {
	return "BookmarkListActivity";
    }

    protected boolean setTitleBarButton(Button button) {
	return false;
    }

    protected void onTitleBarItemClicked(View view) { }
}