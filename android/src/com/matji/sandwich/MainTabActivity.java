package com.matji.sandwich;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionTabHostUtil;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.MainTabHost;
import com.matji.sandwich.widget.title.MainTitle;
import com.matji.sandwich.widget.title.MatistTitle;
import com.matji.sandwich.widget.title.MatstoryTitle;
import com.matji.sandwich.widget.title.PrivateTitle;
import com.matji.sandwich.widget.title.TitleContainer;

// import com.matji.sandwich.http.AsyncTaskTest;

public class MainTabActivity extends BaseTabActivity implements OnTabChangeListener {
    private MainTabHost tabHost;
    private SessionTabHostUtil sessionUtil;
    private Context context;

    private LinearLayout mainTabWrapper;
    private String curSpecLabel;

    public static final String IF_INDEX = "MainTabActivity.index";
    public static final String IF_SUB_INDEX = "MainTabActivity.sub_index";
    public static final int IV_INDEX_MAP = 0;
    public static final int IV_INDEX_POST = 1;
    public static final int IV_INDEX_RANKING = 2;
    public static final int IV_INDEX_CONFIG = 3;

    public static final String SPEC_LABEL1 = "tab1";
    public static final String SPEC_LABEL2 = "tab2";
    public static final String SPEC_LABEL3 = "tab3";

    private TitleContainer curTitle;
    private MainTitle mainTitle;
    private MatstoryTitle matstoryTitle;
    private MatistTitle matistTitle;
    private PrivateTitle privateTitle;

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
        tabHost.addTab(MainTabHost.LOGIN_TAB,
                R.drawable.icon_tapbar_login_selector,
                R.string.main_tab_config,
                new Intent(this, PrivateActivity.class));

        tabHost.setOnTabChangedListener(this);

        mainTitle = new MainTitle(this);
        mainTitle.setTitle(R.string.main_tab_title_matmap);
        matstoryTitle = new MatstoryTitle(this);
        matstoryTitle.setTitle(R.string.main_tab_title_mattalk);
        matistTitle = new MatistTitle(this);
        matistTitle.setTitle(R.string.main_tab_title_matist);
        privateTitle = new PrivateTitle(this);
        privateTitle.setTitle(R.string.main_tab_title_mypage);
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
        curSpecLabel = specLabel;
        switchTitle(curSpecLabel);
    }
    
    
    public void switchTitle(String specLabel) {
        if (curSpecLabel.equals(SPEC_LABEL1)) {
            curTitle = mainTitle;
            mainTabWrapper.removeViewAt(0);
            mainTabWrapper.addView(mainTitle, 0);
        } else if (curSpecLabel.equals(SPEC_LABEL2)) {
            curTitle = matstoryTitle;
            mainTabWrapper.removeViewAt(0);
            mainTabWrapper.addView(matstoryTitle, 0);
        } else if (curSpecLabel.equals(SPEC_LABEL3)) {
            curTitle = matistTitle;
            mainTabWrapper.removeViewAt(0);
            mainTabWrapper.addView(matistTitle, 0);
        } else if (curSpecLabel.equals(MainTabHost.LOGIN_TAB)) {
            curTitle = privateTitle;
            mainTabWrapper.removeViewAt(0);
            mainTabWrapper.addView(privateTitle, 0);
        }
    }

    public TitleContainer getTitlebar() {
        return curTitle;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        privateTitle.notificationValidate();
        invalidateLoginTab();
    }
    
    public void invalidateLoginTab() {
        if (Session.getInstance(this).getPrivateUtil().getLastLoginState()) {
            tabHost.setTabLabel(
                    MainTabHost.LOGIN_TAB, 
                    Session.getInstance(this).getPrivateUtil().getLastLoginUserNick());
        }
    }
}