package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import com.matji.sandwich.error.exception.NotPossibleMatjiException;

public class StoreListActivity extends MatjiListActivity
{
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	intent = getIntent();
	helper = (ListHelper) intent.getParcelableExtra("com.matji.sandwich.listhelper.ListHelper");
	adapter = helper.getAdapter();
	requester = helper.getRequester();
	eventHandler = helper.getEventHandler();
	mContext = getApplicationContext();
    }

    protected ListAdapter createListAdapter() {
	adapter.init(mContext, 
    }
}
