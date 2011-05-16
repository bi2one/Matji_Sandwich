package com.matji.sandwich;

import android.os.Bundle;
import android.view.GestureDetector;
import android.content.Context;
import android.view.MotionEvent;
import android.util.Log;
import android.view.WindowManager;


// import com.matji.sandwich.widget.SwipeView;
public class Flipper1TestActivity extends SwipeActivity {
    public String test = "aaaaa";
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.flipper1);
    }

    protected void onResume() {
	super.onResume();
	Log.d("CREATE flipper1", "flipper1");
    }
}