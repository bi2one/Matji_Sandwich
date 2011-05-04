package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;

public class SliderActivity extends Activity{

    //private constant
    private ArrayList<Activity> activities;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
    }

    public void addActivity(Activity activity){

    }
}
