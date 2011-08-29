package com.matji.sandwich;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.session.SessionTabHostUtil;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.MainTabHost;
import com.matji.sandwich.widget.title.HomeTitle;
import com.matji.sandwich.widget.title.MainTitle;
import com.matji.sandwich.widget.title.UserTitle;
import com.matji.sandwich.widget.title.WritePostTitle;

// import com.matji.sandwich.http.AsyncTaskTest;

public class MainTabActivity extends BaseTabActivity implements OnTabChangeListener {
    private MainTabHost tabHost;
    private SessionTabHostUtil sessionUtil;
    private Context context;

    private LinearLayout mainTabWrapper;

    public static final String IF_INDEX = "MainTabActivity.index";
    public static final String IF_SUB_INDEX = "MainTabActivity.sub_index";
    public static final int IV_INDEX_MAP = 0;
    public static final int IV_INDEX_POST = 1;
    public static final int IV_INDEX_RANKING = 2;
    public static final int IV_INDEX_CONFIG = 3;

    public static final String SPEC_LABEL1 = "tab1";
    public static final String SPEC_LABEL2 = "tab2";
    public static final String SPEC_LABEL3 = "tab3";
    public static final String SPEC_LABEL4 = "tab4";

    public int setMainViewId() {
	return R.id.main_tab_wrapper;
    }
    /**
     * Activity생성시 실행하는 메소드
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
	
        context = getApplicationContext();
        DisplayUtil.setContext(context); // DisplayUtil 초기화
        MatjiConstants.setContext(context); // MatjiContstants 초기화
        sessionUtil = new SessionTabHostUtil(context);

        mainTabWrapper = (LinearLayout)findViewById(R.id.activity_main_tab_title_wrapper);
        mainTabWrapper.addView(new MainTitle(this), 0);

        tabHost = (MainTabHost)getTabHost();

        tabHost.addTab(SPEC_LABEL1,
                R.drawable.icon_tapbar_matmap_selector,
                R.string.main_tab_map,
                new Intent(this, MainMapActivity.class));
        tabHost.addTab(SPEC_LABEL2,
                R.drawable.icon_tapbar_matstory_selector,
                R.string.main_tab_talk,
                new Intent(this, PostTabActivity.class));
        tabHost.addTab(SPEC_LABEL3,
                R.drawable.icon_tapbar_matist_selector,
                R.string.main_tab_ranking,
                new Intent(this, RankingTabActivity.class));
        tabHost.addTab(SPEC_LABEL4,
                R.drawable.icon_tapbar_login_selector,
                R.string.main_tab_config,
                new Intent(this, SettingActivity.class));
                
        tabHost.setOnTabChangedListener(this);

	// AsyncTaskTest tasktest = new AsyncTaskTest();
	// tasktest.execute("1st request");
	// tasktest.execute("2nd request");
	// tasktest.execute("3rd request");
	// tasktest.execute("4th request");
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

    @Override
    public void onTabChanged(String specLabel) {
        if (specLabel.equals(SPEC_LABEL1)) {
            MainTitle title = new MainTitle(this);
            title.setTitle(R.string.main_tab_title_matmap);
            mainTabWrapper.removeViewAt(0);
            mainTabWrapper.addView(title, 0);
        } else if (specLabel.equals(SPEC_LABEL2)) {
            MainTitle title = new MainTitle(this);
            title.setTitle(R.string.main_tab_title_mattalk);
            mainTabWrapper.removeViewAt(0);
            mainTabWrapper.addView(title, 0);
         } else if (specLabel.equals(SPEC_LABEL3)) {
             MainTitle title = new MainTitle(this);
             title.setTitle(R.string.main_tab_title_matist);
             mainTabWrapper.removeViewAt(0);
             mainTabWrapper.addView(title, 0);
         } else if (specLabel.equals(SPEC_LABEL4)) {
             MainTitle title = new MainTitle(this);
             title.setTitle(R.string.main_tab_title_mypage);
             mainTabWrapper.removeViewAt(0);
             mainTabWrapper.addView(title, 0);
        }
    }
}