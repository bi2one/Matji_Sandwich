package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.MatjiConstants;

public class IntroActivity extends BaseActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_intro);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                DisplayUtil.setContext(getApplicationContext()); // DisplayUtil 초기화
                MatjiConstants.setContext(getApplicationContext()); // MatjiContstants 초기화
                Session session  = Session.getInstance(IntroActivity.this);
                if (session.isLogin()) session.unsyncSessionValidate();
                session.notificationValidate();
                startActivity(new Intent(IntroActivity.this, MainTabActivity.class));
                finish();
            }
        };

        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public int setMainViewId() {
        // TODO Auto-generated method stub
        return R.id.activity_intro;
    }
}