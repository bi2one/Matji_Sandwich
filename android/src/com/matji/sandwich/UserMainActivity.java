package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.RoundTabHost;

public class UserMainActivity extends BaseTabActivity {
    private RoundTabHost tabHost;
    public static User user;

    public static final String USER = "UserMainActivity.user";

    public int setMainViewId() {
	return R.id.activity_user_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
    }

    protected void init() {
        super.init();

        setContentView(R.layout.activity_user_main);

        user = getIntent().getParcelableExtra(USER);

        Intent userPostListIntent = new Intent(this, UserPostListActivity.class);
        Intent userImageListIntent = new Intent(this, UserImageListActivity.class);
        Intent userUrlListIntent = new Intent(this, UserUrlListActivity.class);

        tabHost = (RoundTabHost) getTabHost();

        tabHost.addLeftTab("tab1",
                R.string.default_string_post, 
                userPostListIntent);
        tabHost.addCenterTab("tab2",
                R.string.default_string_picture,
                userImageListIntent);
        tabHost.addRightTab("tab3",
                R.string.default_string_webreview,
                userUrlListIntent);
        
        LinearLayout wrapper = (LinearLayout) findViewById(R.id.user_talk_wrapper);
        wrapper.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                    Log.d("Matji", "ACTION UP");
                case MotionEvent.ACTION_DOWN:
                    Log.d("Matji", "ACTION DOWN");
                }
                return false;
            }
        });
     }
    
    public void refresh() {

    }
    
    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }
}