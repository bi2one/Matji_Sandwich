package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;


import android.os.Bundle;
import android.widget.*;

public class AlarmActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);

	TextView textview = new TextView(this);
	textview.setText("sadfasdfas");
	setContentView(textview);
    }
}
