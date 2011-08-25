package com.matji.sandwich;

import android.os.Bundle;
import android.content.Intent;

import com.matji.sandwich.base.TabGroupActivity;

public class WritePostStoreActivityGroup extends TabGroupActivity {
    private WritePostTabActivity parentActivity;
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	parentActivity = (WritePostTabActivity)getParent();
	Intent intent = new Intent(this, WritePostStoreActivity.class);
	startChildActivity("WritePostStoreActivity", intent);
    }

    public void onChecked(String tabId, boolean isCheck) {
	parentActivity.onChecked(tabId, isCheck);
    }
}