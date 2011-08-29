package com.matji.sandwich;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionTabHostUtil;
import com.matji.sandwich.widget.RoundTabHost;

public class RankingTabActivity extends BaseTabActivity {
    private RoundTabHost tabHost;
    private Context context;
    private SessionTabHostUtil sessionUtil;
    private Session session;
    private int lastTab;

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
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        setContentView(R.layout.activity_post_tab);
        tabHost = (RoundTabHost)getTabHost();
        context = getApplicationContext();
        sessionUtil = new SessionTabHostUtil(context);
        session = Session.getInstance(context);
    }

    protected void onResume() {
        super.onResume();
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

        tabHost.addCenterTab("tab3",
                R.string.ranking_tab_country,
                new Intent(this, RankingAllListActivity.class));
        tabHost.addRightTab("tab4",
                R.string.post_tab_all,
                new Intent(this, RankingAllListActivity.class));

        if (!session.isLogin() && lastTab > getTabWidget().getTabCount()-1) {
            lastTab = getTabWidget().getTabCount()-1;
        }
        tabHost.setCurrentTab(lastTab);
    }

    protected void onPause() {
        super.onPause();
        lastTab = tabHost.getCurrentTab();
    }
}
