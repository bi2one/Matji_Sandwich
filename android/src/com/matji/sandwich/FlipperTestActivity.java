package com.matji.sandwich;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.content.*;
import android.view.MotionEvent;
import android.util.Log;
import android.view.WindowManager;
import com.matji.sandwich.widget.StoreListView;
import com.matji.sandwich.widget.StoreNearListView;
import com.matji.sandwich.widget.SwipeView;
import android.view.ViewGroup.LayoutParams;

public class FlipperTestActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.flipper);
	Context context = getApplicationContext();

	StoreListView test = (StoreListView)findViewById(R.id.TestListView);
	StoreNearListView test2 = (StoreNearListView)findViewById(R.id.TestListView2);
	
	test.start(this);
	test2.start(this);
    }
}