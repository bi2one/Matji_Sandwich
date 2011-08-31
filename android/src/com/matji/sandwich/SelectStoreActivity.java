package com.matji.sandwich;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.SelectStoreContents;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.session.SessionMapUtil;

public class SelectStoreActivity extends BaseActivity implements CompletableTitle.Completable,
								 SelectStoreContents.OnClickListener {
    public static final String DATA_STORE = "SelectStoreActivity.store";
    private Context context;
    private CompletableTitle titleBar;
    private SelectStoreContents storeContents;
    private SessionMapUtil sessionMapUtil;

    public int setMainViewId() {
	return R.id.activity_select_store;
    }

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_store);

	context = getApplicationContext();
	sessionMapUtil = new SessionMapUtil(context);
	titleBar = (CompletableTitle)findViewById(R.id.activity_select_store_title);
	titleBar.setTitle(R.string.select_store_activity_title);
	titleBar.setCompletable(this);

	storeContents = (SelectStoreContents)findViewById(R.id.activity_write_post_store_contents);
	storeContents.init(this);
	storeContents.refresh(sessionMapUtil.getNEBound(), sessionMapUtil.getSWBound());
	storeContents.setOnClickListener(this);
    }

    public void complete() {
	Log.d("=====", "select store activity complete");
    }

    public void onSearchLocationClick(View v) {
	Log.d("=====", "search location");
    }

    public void onWriteStoreClick(View v) {
	Log.d("=====", "write store");
    }
}