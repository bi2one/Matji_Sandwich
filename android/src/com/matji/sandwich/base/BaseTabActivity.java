package com.matji.sandwich.base;

import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.data.MatjiData;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public abstract class BaseTabActivity extends TabActivity {
	@Override
	public void setContentView(int layoutResID) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(layoutResID);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
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
}
