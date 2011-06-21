package com.matji.sandwich.base;

import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.base.ActivityEnterForeGroundDetector.ActivityEnterForeGroundListener;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.session.Session;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Window;

public abstract class BaseTabActivity extends TabActivity implements ActivityEnterForeGroundListener {
	
	public void didEnterForeGround(){
		Session session  = Session.getInstance(this);
		if (session.isLogin()){
			session.sessionValidate(null, this);
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Log.d("LifeCycle", "onResume at " + this.getClass());
		ActivityEnterForeGroundDetector.getInstance().setState(ActivityEnterForeGroundDetector.ActivityState.ONRESUME, this);
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d("LifeCycle", "onRestart at " + this.getClass());
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("LifeCycle", "onPause at " + this.getClass());
		ActivityEnterForeGroundDetector.getInstance().setState(ActivityEnterForeGroundDetector.ActivityState.ONPAUSE, this);
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("LifeCycle", "onStop at " + this.getClass());
		ActivityEnterForeGroundDetector.getInstance().setState(ActivityEnterForeGroundDetector.ActivityState.ONSTOP, this);
	}
	
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
