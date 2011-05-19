package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TestTabItem3 extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView t = new TextView(this);
		t.setText("탭3번");
		setContentView(t);
	}
}