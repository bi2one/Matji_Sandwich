package com.matji.sandwich;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionTabHostUtil;
import com.matji.sandwich.widget.RoundTabHost;

public class PostTabActivity extends BaseTabActivity {
    private RoundTabHost tabHost;
    private Context context;
    private Session session;
    private SessionTabHostUtil sessionUtil;
    private int lastTab = 0;
    private boolean lastLoginState;
    private boolean isNotFirst;

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
        isNotFirst = false;
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        setContentView(R.layout.activity_post_tab);
        tabHost = (RoundTabHost)getTabHost();
        context = getApplicationContext();
        session = Session.getInstance(context);
        sessionUtil = new SessionTabHostUtil(context);
    }

    protected void onResume() {
        super.onResume();
        if (isNotFirst && lastLoginState != session.isLogin()) {
            reload();
        }

        lastLoginState = session.isLogin();
        isNotFirst = true;
    }

    protected void onAfterResume() {
        int baseIndex = sessionUtil.getSubTabIndex();
        sessionUtil.flush();
        if (baseIndex >= 0 && !session.isLogin()) {
            baseIndex = 0;
        }
        if (baseIndex >= 0)  {
            tabHost.setCurrentTab(baseIndex);
        }
    }

    @Override
    protected void onFlowResume() {
        super.onFlowResume();
    }

    private void reload() {
        tabHost.setCurrentTab(0);
        tabHost.clearAllTabs();

        if (session.isLogin()) {
            Intent friendIntent = new Intent(this, PostListActivity.class);
            friendIntent.putExtra(PostListActivity.TYPE, PostListActivity.TYPE_FRIEND);
            tabHost.addLeftTab("tab1",
                    R.string.post_tab_friend,
                    friendIntent);

            tabHost.addCenterTab("tab2",
                    R.string.post_tab_near,
                    new Intent(this, PostNearListActivity.class));
        } else {
            tabHost.addLeftTab("tab2",
                    R.string.post_tab_near,
                    new Intent(this, PostNearListActivity.class));
        }

        Intent domesticIntent = new Intent(this, PostListActivity.class);
        domesticIntent.putExtra(PostListActivity.TYPE, PostListActivity.TYPE_DOMESTIC);
        tabHost.addCenterTab("tab3",
                R.string.post_tab_country,
                domesticIntent);

        tabHost.addRightTab("tab4",
                R.string.post_tab_all,
                new Intent(this, PostListActivity.class));

        if (!session.isLogin() && lastTab > getTabWidget().getTabCount()-1) {
            lastTab = getTabWidget().getTabCount()-1;
        }
        tabHost.setCurrentTab(lastTab);
    }

    @Override
    protected void onNotFlowResume() {
        super.onNotFlowResume();
        reload();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lastTab = tabHost.getCurrentTab();
    }
}