package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.Session.LoginListener;
import com.matji.sandwich.session.SessionTabHostUtil;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.MainTabHost;
import com.matji.sandwich.widget.title.MainTabTitle;
import com.matji.sandwich.widget.title.MainTitle;
import com.matji.sandwich.widget.title.MatistTitle;
import com.matji.sandwich.widget.title.MatstoryTitle;
import com.matji.sandwich.widget.title.PrivateTitle;
import com.matji.sandwich.widget.title.SettingTitle;
import com.matji.sandwich.widget.title.TitleContainer;

public class MainTabActivity extends BaseTabActivity implements OnTabChangeListener, LoginListener {
    private MainTabHost tabHost;
    private Session session;
    private SessionTabHostUtil sessionUtil;

    private LinearLayout mainTabWrapper;
    private String curSpecLabel;

    public static final String IF_INDEX = "MainTabActivity.index";
    public static final String IF_SUB_INDEX = "MainTabActivity.sub_index";
    public static final int IV_INDEX_MAP = 0;
    public static final int IV_INDEX_POST = 1;
    public static final int IV_INDEX_RANKING = 2;
    public static final int IV_INDEX_CONFIG = 3;
    public static final int IV_INDEX_LOGIN = 4;

    public static final String SPEC_LABEL1 = "tab1";
    public static final String SPEC_LABEL2 = "tab2";
    public static final String SPEC_LABEL3 = "tab3";

    private ArrayList<MainTabTitle> titles;
    private MainTitle mainTitle;
    private MatstoryTitle matstoryTitle;
    private MatistTitle matistTitle;
    private PrivateTitle privateTitle;
    private SettingTitle settingTitle;    

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
        
        session = Session.getInstance(this);
        session.addLoginListener(this);
        sessionUtil = new SessionTabHostUtil(this);

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
        

        Intent userTabIntent = new Intent(this, UserProfileTabActivity.class);
        userTabIntent.putExtra(UserProfileTabActivity.IS_MAIN_TAB_ACTIVITY, true);
        tabHost.addTab(MainTabHost.LOGIN_TAB,
                R.drawable.icon_tapbar_login_selector,
                R.string.main_tab_config,
                userTabIntent);

        tabHost.setOnTabChangedListener(this);

        
        titles = new ArrayList<MainTabTitle>();
        mainTitle = new MainTitle(this);
        mainTitle.setLogo();
        titles.add(mainTitle);
        matstoryTitle = new MatstoryTitle(this);
        matstoryTitle.setTitle(R.string.main_tab_title_mattalk);
        titles.add(matstoryTitle);
        matistTitle = new MatistTitle(this);
        matistTitle.setTitle(R.string.main_tab_title_matist);
        titles.add(matistTitle);
        privateTitle = new PrivateTitle(this);
        privateTitle.setTitle(R.string.main_tab_title_mypage);
        titles.add(privateTitle);
        settingTitle = new SettingTitle(this);
        settingTitle.setTitle(R.string.main_tab_title_login);
        titles.add(settingTitle);
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
        KeyboardUtil.hideKeyboard(this);
    }


    public void switchTitle(String specLabel) {
        if (curSpecLabel.equals(SPEC_LABEL1)) {
            switchTitle(mainTitle);
        } else if (curSpecLabel.equals(SPEC_LABEL2)) {
            switchTitle(matstoryTitle);
        } else if (curSpecLabel.equals(SPEC_LABEL3)) {
            switchTitle(matistTitle);
        } else if (curSpecLabel.equals(MainTabHost.LOGIN_TAB)) {
            if (session.isLogin()) {
                switchTitle(privateTitle);
            } else {
                switchTitle(settingTitle);
            }
        }
    }
    
    public void switchTitle(TitleContainer title) {
        mainTabWrapper.removeViewAt(0);
        mainTabWrapper.addView(title, 0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        notificationValidate();
        syncTitle();
        loginTabSync();
    }

    public void loginTabSync() {
        if (session.isLogin()) {
            tabHost.setTabLabel(
                    MainTabHost.LOGIN_TAB, 
                    session.getCurrentUser().getNick());
        }
    }

    public void syncTitle() {
        int curTabPos = tabHost.getCurrentTab();
        if (curTabPos == IV_INDEX_CONFIG && !session.isLogin()) {
            curTabPos = IV_INDEX_LOGIN;
        }
        
        switchTitle((TitleContainer) titles.get(curTabPos));
        titles.get(curTabPos).notificationValidate();
    }
    
    @Override
    public void preLogin() {}

    @Override
    public void postLogin() {
        syncTitle();
    }
    
    protected void notificationValidate() {
        for (MainTabTitle title : titles) {
            title.notificationValidate();
        }
        
        int alarmCount = session.getPrivateUtil().getNewNoticeCount() + session.getPrivateUtil().getNewAlarmCount();
        tabHost.setAlarmCount(alarmCount);
    }
}