package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.widget.MainTabHost;

import android.content.Intent;
import android.os.Bundle;

public class MainTabActivity extends BaseTabActivity {
    private MainTabHost tabHost;
    
    public static final String IF_INDEX = "index";
    public static final int IV_INDEX_STORE = 1;
    public static final int IV_INDEX_POST = 2;
    
    /**
     * Activity생성시 실행하는 메소드
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_tab);

	tabHost = (MainTabHost)getTabHost();

	DisplayUtil.setContext(getApplicationContext());

	tabHost.addTab("tab1",
		       R.drawable.icon,
		       R.string.main_tab_map,
		       new Intent(this, MainMapActivity.class));
	tabHost.addTab("tab2",
		       R.drawable.icon,
		       R.string.main_tab_talk,
		       new Intent(this, PostTabActivity.class));
	tabHost.addTab("tab3",
		       R.drawable.icon,
		       R.string.main_tab_ranking,
		       new Intent(this, StoreSliderActivity.class));
	tabHost.addTab("tab4",
		       R.drawable.icon,
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
    }
}