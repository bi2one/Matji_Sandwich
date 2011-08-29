package com.matji.sandwich;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.session.SessionTabHostUtil;
import com.matji.sandwich.widget.RoundTabHost;

public class PostTabActivity extends BaseTabActivity {
    private RoundTabHost tabHost;
    private Context context;
    private SessionTabHostUtil sessionUtil;
    private int lastTab = 0;
    private ActivityStartable lastStartedChild;

    public int setMainViewId() {
        return R.id.activity_post_tab;
    }

    /**
     * Activity생성시 실행하는 메소드
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        setContentView(R.layout.activity_post_tab);
        tabHost = (RoundTabHost)getTabHost();
        context = getApplicationContext();
        sessionUtil = new SessionTabHostUtil(context);
    }

    protected void onResume() {
        super.onResume();        


        tabHost.setCurrentTab(0);
        tabHost.clearAllTabs();

        if (sessionUtil.isLogin()) {
            tabHost.addLeftTab("tab1",
                    R.string.post_tab_friend,
                    new Intent(this, PostListActivity.class));
            tabHost.addCenterTab("tab2",
                    R.string.post_tab_near,
                    new Intent(this, PostNearListActivity.class));
        } else {
            tabHost.addLeftTab("tab2",
                    R.string.post_tab_near,
                    new Intent(this, PostNearListActivity.class));
        }

        tabHost.addRightTab("tab3",
                R.string.post_tab_all,
                new Intent(this, PostListActivity.class));

        if (!sessionUtil.isLogin() && lastTab > getTabWidget().getTabCount()-1) {
            lastTab = getTabWidget().getTabCount()-1;
        }
        tabHost.setCurrentTab(lastTab);
        
        int baseIndex = sessionUtil.getSubTabIndex();
        sessionUtil.flush();
        if (!sessionUtil.isLogin()) {
            baseIndex = 0;
        }
        if (baseIndex >= 0)  {
            tabHost.setCurrentTab(baseIndex);
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        lastTab = tabHost.getCurrentTab();
    }
}
