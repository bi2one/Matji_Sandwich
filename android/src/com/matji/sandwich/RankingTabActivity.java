package com.matji.sandwich;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.PostListView;
import com.matji.sandwich.widget.MyPostListView;
import com.matji.sandwich.widget.RoundTabHost;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.session.SessionTabHostUtil;

public class RankingTabActivity extends BaseTabActivity {
    private RoundTabHost tabHost;
    private Context context;
    private SessionTabHostUtil sessionUtil;
    
    /**
     * Activity생성시 실행하는 메소드
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_post_tab);
	tabHost = (RoundTabHost)getTabHost();
	context = getApplicationContext();
	sessionUtil = new SessionTabHostUtil(context);

	tabHost.addLeftTab("tab1",
			   R.string.post_tab_friend,
			   new Intent(this, PostListActivity.class));
	tabHost.addCenterTab("tab2",
			     R.string.post_tab_near,
			     new Intent(this, PostNearListActivity.class));
	tabHost.addRightTab("tab3",
			     R.string.post_tab_all,
			     new Intent(this, PostListActivity.class));
    }

    protected void onResume() {
	super.onResume();
	int baseIndex = sessionUtil.getSubTabIndex();
	sessionUtil.flush();
	if (baseIndex >= 0)  {
	    tabHost.setCurrentTab(baseIndex);
	}
    }
}
