package com.matji.sandwich.base;

import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.base.ActivityEnterForeGroundDetector.ActivityEnterForeGroundListener;
import com.matji.sandwich.session.Session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RelativeLayout;

public abstract class BaseActivity extends Activity implements ActivityEnterForeGroundListener, Identifiable {
	public static final int REQUEST_EXTERNAL_SERVICE_LOGIN = 22;	
	public static final int LOGIN_ACTIVITY = 100;
	public static final int POST_ACTIVITY = 101;
	public static final int WRITE_POST_ACTIVITY = 102;
	public static final int WRITE_COMMENT_ACTIVITY = 103;	
	public static final int RECEIVED_USER_ACTIVITY = 104;
    private RelativeLayout mainViewGroup;
    private boolean isFlow;

    public abstract int setMainViewId();

    protected RelativeLayout getMainView() {
	return mainViewGroup;
    }

    protected void init() {
	setIsFlow(false);
	mainViewGroup = (RelativeLayout)findViewById(setMainViewId());
    }

    public void setIsFlow(boolean isFlow) {
	this.isFlow = isFlow;
    }
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
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
	init();
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
}
