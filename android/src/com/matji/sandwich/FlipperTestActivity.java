package com.matji.sandwich;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.content.*;
import android.view.MotionEvent;
import android.util.Log;
import android.view.WindowManager;

import com.matji.sandwich.widget.*;
import android.view.ViewGroup.LayoutParams;

public class FlipperTestActivity extends Activity {
	StoreListView test;
	StoreNearListView test2;
	
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.flipper);
	Context context = getApplicationContext();


	StoreListView test = (StoreListView)findViewById(R.id.TestListView);
	StoreNearListView test2 = (StoreNearListView)findViewById(R.id.TestListView2);
	PostListView test3 = (PostListView)findViewById(R.id.TestListView3);
	
	test.start(this);
	test2.start(this);
	test3.start(this);
    }
}