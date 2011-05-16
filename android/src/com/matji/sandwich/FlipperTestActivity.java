package com.matji.sandwich;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.content.Context;
import android.view.MotionEvent;
import android.util.Log;
import android.view.WindowManager;
import com.matji.sandwich.widget.TestListView;
import com.matji.sandwich.widget.SwipeView;
import android.view.ViewGroup.LayoutParams;

// import com.matji.sandwich.widget.SwipeView;
public class FlipperTestActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.flipper);
	Context context = getApplicationContext();

	SwipeView sv = (SwipeView)findViewById(R.id.SwipeView);
	TestListView test = (TestListView)findViewById(R.id.TestListView);
	TestListView test2 = (TestListView)findViewById(R.id.TestListView2);
	TestListView test3 = (TestListView)findViewById(R.id.TestListView3);

	test.start(this);
	test2.start(this);
	test3.start(this);

	TestListView addTest = new TestListView(context, null);
	sv.addView(addTest);
	addTest.start(this);
	
	// get window width
	// WindowManager mWinMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
	// int displayWidth = mWinMgr.getDefaultDisplay().getWidth();
    }
}