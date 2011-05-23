package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import com.matji.sandwich.location.GpsManager;

public class MainMapActivity extends MapActivity{

	//private constant
	private final static int GROUP_ID_ONE=0;
	private final static int ZOOM_IN_ID =1;
	private final static int ZOOM_OUT_ID=2;
	private final static int GOTO = 3;

	private GpsManager gpsmanager;
	private MapView map;
	private EditText e;
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_map);
		map = (MapView) findViewById(R.id.main_map_map);
		map.setBuiltInZoomControls(true);
		//		e = (EditText) findViewById(R.id.main_map_search_bar);
		//		Button b = (Button) findViewById(R.id.main_map_gps_button);
		//		b.requestFocus();
		//		e.setOnFocusChangeListener(new MyFocusChangeListener());
	}

	private void setMarker(Location loc){

	}

	private void setSmallmarker(Location loc){

	}

	protected boolean isRouteDisplayed() {
		return true;
	}

	class MyFocusChangeListener implements OnFocusChangeListener {
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(hasFocus) {
				Log.d("Matji", hasFocus + "");
				((EditText) v).setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			}
			else {
				Log.d("Matji", hasFocus + "");
				((EditText) v).setLayoutParams(new RelativeLayout.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT));
			}
		}
	}
}
