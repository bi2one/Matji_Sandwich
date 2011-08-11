package com.matji.sandwich.base;

import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.base.ActivityEnterForeGroundDetector.ActivityEnterForeGroundListener;
import com.matji.sandwich.session.Session;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public abstract class BaseTabActivity extends TabActivity implements ActivityEnterForeGroundListener, Identifiable {
	protected static final int LOGIN_ACTIVITY = 1;
	protected static final int WRITE_POST_ACTIVITY = 2;
	
	protected void init() {}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks whether a hardware keyboard is available
	    if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
	    	Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
	        Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
	    }
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
		super.onRestart();
		Log.d("LifeCycle", "onRestart at " + this.getClass());
	}
	
	@Override
	protected void onPause() {
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.Theme_RemoveOverlay);
		super.setContentView(layoutResID);
	}
}