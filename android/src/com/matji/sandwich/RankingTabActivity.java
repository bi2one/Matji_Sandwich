package com.matji.sandwich;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.RoundTabHost;

public class RankingTabActivity extends BaseTabActivity {
    private RoundTabHost tabHost;
    private Context context;
    private Session session;
    private int lastTab;
    private boolean lastLoginState;
    private boolean isFirst;

    public int setMainViewId() {
        return R.id.activity_ranking_tab;
    }

    /**
     * Activity생성시 실행하는 메소드
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        isFirst = true;
        setContentView(R.layout.activity_post_tab);
        tabHost = (RoundTabHost)getTabHost();
        context = getApplicationContext();
        session = Session.getInstance(context);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst && lastLoginState != session.isLogin()) {
            reload();
        }
        
        lastLoginState = session.isLogin();
        isFirst = false;
    }
    
    public void reload() {
        tabHost.setCurrentTab(0);
        tabHost.clearAllTabs();
        
        if (session.isLogin()) {
            tabHost.addLeftTab("tab1",
                    R.string.ranking_tab_friend,
                    new Intent(this, RankingFriendListActivity.class));
            tabHost.addCenterTab("tab2",
                    R.string.ranking_tab_near,
                    new Intent(this, RankingNearListActivity.class));
        } else {
            tabHost.addLeftTab("tab2",
                    R.string.ranking_tab_near,
                    new Intent(this, RankingNearListActivity.class));
        }

        tabHost.addRightTab("tab3",
                R.string.ranking_tab_country,
                new Intent(this, RankingAllListActivity.class));
//        tabHost.addRightTab("tab4",
//                R.string.ranking_tab_all,
//                new Intent(this, RankingAllListActivity.class));

        if (!session.isLogin() && lastTab > getTabWidget().getTabCount()-1) {
            lastTab = getTabWidget().getTabCount()-1;
        }
        tabHost.setCurrentTab(lastTab);
    }
    
    @Override
    protected void onNotFlowResume() {
        // TODO Auto-generated method stub
        super.onNotFlowResume();
        reload();
    }

    protected void onPause() {
        super.onPause();
        lastTab = tabHost.getCurrentTab();
    }
}
