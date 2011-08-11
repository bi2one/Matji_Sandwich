package com.matji.sandwich.base;

import com.matji.sandwich.ActivityStartable;
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

public abstract class BaseTabActivity extends TabActivity implements ActivityEnterForeGroundListener, Base {
    protected static final int LOGIN_ACTIVITY = 1;
    protected static final int WRITE_POST_ACTIVITY = 2;
    private ActivityStartable lastStartedChild;
    private boolean isFlow;

    protected void init() {
	setIsFlow(false);
    }

    public void setIsFlow(boolean isFlow) {
	this.isFlow = isFlow;
    }
	
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

    public void tabStartActivityForResult(Intent intent, int requestCode, ActivityStartable child) {
	lastStartedChild = child;
	startActivityForResult(intent, requestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	lastStartedChild.activityResultDelivered(requestCode, resultCode, data);
    }    
}