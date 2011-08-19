package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.MainTabHost;
import com.matji.sandwich.session.SessionTabHostUtil;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class MainTabActivity extends BaseTabActivity {
    private MainTabHost tabHost;
    private SessionTabHostUtil sessionUtil;
    private Context context;
    
    public static final String IF_INDEX = "MainTabActivity.index";
    public static final String IF_SUB_INDEX = "MainTabActivity.sub_index";
    public static final int IV_INDEX_MAP = 0;
    public static final int IV_INDEX_POST = 1;
    public static final int IV_INDEX_RANKING = 2;
    public static final int IV_INDEX_CONFIG = 3;
    
    /**
     * Activity생성시 실행하는 메소드
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	context = getApplicationContext();
	DisplayUtil.setContext(context); // DisplayUtil 초기화
	MatjiConstants.setContext(context); // MatjiContstants 초기화
	sessionUtil = new SessionTabHostUtil(context);
	setContentView(R.layout.activity_main_tab);

	tabHost = (MainTabHost)getTabHost();

	tabHost.addTab("tab1",
		       R.drawable.icon_tabbar_matmap,
		       R.string.main_tab_map,
		       new Intent(this, MainMapActivity.class));
	tabHost.addTab("tab2",
		       R.drawable.icon_tabbar_matstory,
		       R.string.main_tab_talk,
		       new Intent(this, PostTabActivity.class));
	tabHost.addTab("tab3",
		       R.drawable.icon_tabbar_matist,
		       R.string.main_tab_ranking,
		       new Intent(this, RankingTabActivity.class));
	tabHost.addTab("tab4",
		       R.drawable.icon_tabbar_login,
		       R.string.main_tab_config,
		       new Intent(this, SettingActivity.class));
    }

    /**
     * 이 Activity로 intent를 이용해서 다시 돌아왔을 때, 실행하는
     * 메소드. 현재 탭을 지정한 인덱스로 설정한다.
     *
     * @param intent 탭 이동 정보가 담긴 Intent
     */
    protected void onNewIntent(Intent intent) {
	super.onNewIntent(intent);
	tabHost.setCurrentTab(intent.getIntExtra(IF_INDEX, 0));
	sessionUtil.setSubTabIndex(intent.getIntExtra(IF_SUB_INDEX, 0));
    }
}