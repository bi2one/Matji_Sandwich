package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.http.util.DisplayUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.util.Log;

public class StoreRegisterActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// DisplayUtil.setContext(getApplicationContext());
	setContentView(R.layout.activity_main_tab);
    }
}