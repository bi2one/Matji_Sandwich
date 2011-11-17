package com.matji.sandwich.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.maps.MapActivity;
import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.base.ActivityEnterForeGroundDetector.ActivityEnterForeGroundListener;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.MatjiConstants;

public abstract class BaseMapActivity extends MapActivity implements ActivityEnterForeGroundListener, Identifiable {
	private boolean isFlow;
	private ViewGroup mainViewGroup;
	private HttpRequestManager requestManager = HttpRequestManager.getInstance();

	public abstract int setMainViewId();

	protected ViewGroup getMainView() {
		return mainViewGroup;
	}

	private void init() {
		setIsFlow(false);
	}

	public void setIsFlow(boolean isFlow) {
		this.isFlow = isFlow;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		DisplayUtil.setContext(getApplicationContext()); // DisplayUtil 초기화
		MatjiConstants.setContext(getApplicationContext()); // MatjiContstants 초기화

		super.onCreate(savedInstanceState);
		init();
	}

	@Override
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
		ImageLoader.clearCache(getApplicationContext());
		Session session  = Session.getInstance(this);
		if (session.isLogin()){
			session.sessionValidate(null, getMainView());
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
		requestManager.cancelAllTask();
		ActivityEnterForeGroundDetector.getInstance().setState(ActivityEnterForeGroundDetector.ActivityState.ONPAUSE, this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("LifeCycle", "onStop at " + this.getClass());
		ActivityEnterForeGroundDetector.getInstance().setState(ActivityEnterForeGroundDetector.ActivityState.ONSTOP, this);
		Session.getInstance(this).getConcretePreferenceProvider().commit(getApplicationContext());
	}

	@Override
	public void setContentView(int layoutResID) {
		if (this.getParent() == null){
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setTheme(R.style.Theme_RemoveOverlay);
		}
		super.setContentView(layoutResID);
		mainViewGroup = (ViewGroup)findViewById(setMainViewId());

		if (mainViewGroup != null && mainViewGroup.getBackground() == null) {
			mainViewGroup.setBackgroundResource(R.drawable.bg_01);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.d("LifeCycle", "onResume at " + this.getClass());
		ActivityEnterForeGroundDetector.getInstance().setState(ActivityEnterForeGroundDetector.ActivityState.ONRESUME, this);

		if (!isFlow) {
			Log.d("LifeCycle", "onNotFlowResume at " + this.getClass());
			onNotFlowResume();
		} else {
			Log.d("LifeCycle", "onFlowResume at " + this.getClass());
			onFlowResume();
		}
		setIsFlow(false);
	}

	protected void onNotFlowResume() { }
	protected void onFlowResume() { }

	public void startActivity(Intent intent) {
		setIsFlow(true);
		super.startActivity(intent);
	}

	public void startActivityForResult(Intent intent, int requestCode) {
		setIsFlow(true);
		super.startActivityForResult(intent, requestCode);
	}    

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case BaseActivity.TERMS_ACTIVITY:
			if (resultCode != RESULT_OK) {
				Session.getInstance(this).logout();
			}
			break;
		}
	}
}