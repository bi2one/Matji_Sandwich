package com.matji.sandwich.base;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.matji.sandwich.ActivityStartable;
import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.base.ActivityEnterForeGroundDetector.ActivityEnterForeGroundListener;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.MatjiConstants;

public abstract class BaseTabActivity extends TabActivity implements ActivityEnterForeGroundListener, Identifiable {
    private ActivityStartable lastStartedChild;
    private boolean isFirstResume = true;
    private boolean isFlow;
    private RelativeLayout mainViewGroup;
    private HttpRequestManager requestManager = HttpRequestManager.getInstance();

    public abstract int setMainViewId();

    protected RelativeLayout getMainView() {
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
        ImageLoader.clearCache(getApplicationContext());
        Session session  = Session.getInstance(this);
        if (session.isLogin()){
            session.sessionValidate(null, getMainView());
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
            Log.d("LifeCycle", "onAfterResume at " + this.getClass());
            onAfterResume();
        } else {
            Log.d("LifeCycle", "onFlowResume at " + this.getClass());
            onFlowResume();
            Log.d("LifeCycle", "onAfterResume at " + this.getClass());
            onAfterResume();
        }
        setIsFlow(false);
    }

    protected void onNotFlowResume() { }
    protected void onFlowResume() { }
    protected void onAfterResume() { }

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
        if (isFirstResume) requestWindowFeature(Window.FEATURE_NO_TITLE);
        isFirstResume = false;
        setTheme(R.style.Theme_RemoveOverlay);
        super.setContentView(layoutResID);
        mainViewGroup = (RelativeLayout)findViewById(setMainViewId());
        if (mainViewGroup != null && mainViewGroup.getBackground() == null) {
            mainViewGroup.setBackgroundResource(R.drawable.bg_01);
        }
    }

    public void tabStartActivityForResult(Intent intent, int requestCode, ActivityStartable activityStartable) {
        lastStartedChild = activityStartable;
        startActivityForResult(intent, requestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case BaseActivity.TERMS_ACTIVITY:
            if (resultCode != RESULT_OK) {
                Session.getInstance(this).logout();
            }
            break;
        }
        if (data != null) {
            lastStartedChild.activityResultDelivered(requestCode, resultCode, data);        	
        }
    }
}