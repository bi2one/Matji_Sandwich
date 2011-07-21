package com.matji.sandwich.base;

import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.Window;

import com.google.android.maps.MapActivity;
import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.base.ActivityEnterForeGroundDetector.ActivityEnterForeGroundListener;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.session.Session;

public abstract class BaseMapActivity extends MapActivity implements ActivityEnterForeGroundListener{	
	protected static final int LOGIN_ACTIVITY = 1;
	protected static final int WRITE_POST_ACTIVITY = 2;
	
	public boolean loginRequired(){
		Session session = Session.getInstance(this);
		if (!session.isLogin()) {
			startActivity(new Intent(getApplicationContext(), LoginActivity.class));
			return false;
		}
		
		return true;
	}
	
	public void didEnterForeGround(){
		Log.d("LifeCycle", "ENTER");
		Session session  = Session.getInstance(this);
		if (session.isLogin()){
			session.sessionValidate(null, this);
		}
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
		if (this.getParent() == null){
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setTheme(R.style.Theme_RemoveOverlay);
		}
		super.setContentView(layoutResID);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Log.d("LifeCycle", "onResume at " + this.getClass());
		ActivityEnterForeGroundDetector.getInstance().setState(ActivityEnterForeGroundDetector.ActivityState.ONRESUME, this);
	}
	
	private void preFinish() {
		HttpRequestManager.getInstance(this).cancelTask();
		SharedMatjiData.getInstance().pop();
	}	
	
	private void preStart(MatjiData data) {
		HttpRequestManager.getInstance(this).cancelTask();
		SharedMatjiData.getInstance().push(data);
	}
	
	public void finishWithMatjiData() {
		// TODO Auto-generated method stub
		preFinish();
		super.finish();
	}

	public void finishActivityWithMatjiData(int requestCode) {
		// TODO Auto-generated method stub
		preFinish();
		super.finishActivity(requestCode);
	}

	public void finishActivityFromChildWithMatjiData(Activity child, int requestCode) {
		// TODO Auto-generated method stub
		preFinish();
		super.finishActivityFromChild(child, requestCode);
	}

	public void finishFromChildWithMatjiData(Activity child) {
		// TODO Auto-generated method stub
		preFinish();
		super.finishFromChild(child);
	}

	public void startActivityWithMatjiData(Intent intent, MatjiData data) {
		// TODO Auto-generated method stub
		preStart(data);
		super.startActivity(intent);
	}

	public void startActivityForResultWithMatjiData(Intent intent, int requestCode, MatjiData data) {
		// TODO Auto-generated method stub
		preStart(data);
		super.startActivityForResult(intent, requestCode);
	}

	public void startActivityFromChildWithMatjiData(Activity child, Intent intent, int requestCode, MatjiData data) {
		// TODO Auto-generated method stub
		preStart(data);
		super.startActivityFromChild(child, intent, requestCode);
	}

	public boolean startActivityIfNeededWithMatjiData(Intent intent, int requestCode, MatjiData data) {
		// TODO Auto-generated method stub
		preStart(data);
		return super.startActivityIfNeeded(intent, requestCode);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}