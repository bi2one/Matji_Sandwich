package com.matji.sandwich.base;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.google.android.maps.MapActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.data.MatjiData;

public abstract class BaseMapActivity extends MapActivity {	
	protected abstract String usedTitleBar();

	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		String titleText = usedTitleBar();
		if (titleText != null) {
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			super.setContentView(layoutResID);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
			TextView titleView = (TextView) findViewById(R.id.title);
			titleView.setText(titleText);
		} else {
			super.setContentView(layoutResID);
		}
	}
	
	public void finishWithMatjiData() {
		// TODO Auto-generated method stub
		SharedMatjiData.getInstance().pop();
		super.finish();
	}

	public void finishActivityWithMatjiData(int requestCode) {
		// TODO Auto-generated method stub
		SharedMatjiData.getInstance().pop();
		super.finishActivity(requestCode);
	}

	public void finishActivityFromChildWithMatjiData(Activity child, int requestCode) {
		// TODO Auto-generated method stub
		SharedMatjiData.getInstance().pop();
		super.finishActivityFromChild(child, requestCode);
	}

	public void finishFromChildWithMatjiData(Activity child) {
		// TODO Auto-generated method stub
		SharedMatjiData.getInstance().pop();
		super.finishFromChild(child);
	}

	public void startActivityWithMatjiData(Intent intent, MatjiData data) {
		Log.d("Matji", "START ACTIVITY WITH");
		// TODO Auto-generated method stub
		SharedMatjiData.getInstance().push(data);
		super.startActivity(intent);
	}

	public void startActivityForResultWithMatjiData(Intent intent, int requestCode, MatjiData data) {
		// TODO Auto-generated method stub
		SharedMatjiData.getInstance().push(data);
		super.startActivityForResult(intent, requestCode);
	}

	public void startActivityFromChildWithMatjiData(Activity child, Intent intent, int requestCode, MatjiData data) {
		// TODO Auto-generated method stub
		SharedMatjiData.getInstance().push(data);
		super.startActivityFromChild(child, intent, requestCode);
	}

	public boolean startActivityIfNeededWithMatjiData(Intent intent, int requestCode, MatjiData data) {
		// TODO Auto-generated method stub
		SharedMatjiData.getInstance().push(data);
		return super.startActivityIfNeeded(intent, requestCode);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
