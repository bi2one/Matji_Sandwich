package com.matji.sandwich.base;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;

import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.base.ActivityEnterForeGroundDetector.ActivityEnterForeGroundListener;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.MatjiConstants;

public abstract class BaseListActivity extends ListActivity implements ActivityEnterForeGroundListener, Identifiable {	
    private boolean isFlow;
    private ViewGroup mainViewGroup;
    private HttpRequestManager requestManager = HttpRequestManager.getInstance();

    public abstract int setMainViewId();
    
    protected ViewGroup getMainView() {
        return mainViewGroup;
    }

    public void setIsFlow(boolean isFlow) {
        this.isFlow = isFlow;
    }

    protected void init() {
        setIsFlow(false);
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
        Session.getInstance(this).getConcretePreferenceProvider().commit();
    }


    @Override
    public void setContentView(int layoutResID) {
        if (this.getParent() == null){
            requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
            setTheme(R.style.Theme_RemoveOverlay);
        }
        super.setContentView(layoutResID);
        mainViewGroup = (ViewGroup)findViewById(setMainViewId());
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
