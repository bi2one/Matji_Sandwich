package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.LoginView;
import com.matji.sandwich.widget.RoundTabHost;
import com.matji.sandwich.widget.UserProfileView;

public class UserProfileTabActivity extends BaseTabActivity implements Loginable {

    public static User user;
    private RoundTabHost tabHost;
    private Session session;
    private boolean isMainTabActivity;
    private boolean isMyPage;

    private LoginView loginView;
    private Intent profileIntent;
    private Intent tagIntent;
    
    public static final String USER = "UserProfileTabActivity.user";
    public static final String IS_MAIN_TAB_ACTIVITY = "UserProfileTabActivity.is_main_tab_activity";
    
    public int setMainViewId() {
        return R.id.activity_user_profile_tab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        session = Session.getInstance(this);
        isMainTabActivity = getIntent().getBooleanExtra(IS_MAIN_TAB_ACTIVITY, false);

        setContentView(R.layout.activity_user_profile_tab);

        tabHost = (RoundTabHost) getTabHost();
        loginView = (LoginView) findViewById(R.id.login_view);
        
        user = (isMainTabActivity) ? session.getCurrentUser() : (User) getIntent().getParcelableExtra(USER);
        setUser(user);
    }

    public void setUser(User user) {    // user가 저장되면 탭도 같이 바뀌어야 함.
    	
    	UserProfileTabActivity.user = user;
        profileIntent = new Intent(this, UserProfileActivity.class);
        profileIntent.putExtra(UserProfileActivity.IS_MAIN_TAB_ACTIVITY, isMainTabActivity);

        tagIntent = new Intent(this, UserTagCloudActivity.class);
        tagIntent.putExtra(UserTagCloudActivity.IS_MAIN_TAB_ACTIVITY, isMainTabActivity);
    }
    
    private void syncTab() {        // 로그인 화면을 위해 어쩔수 없이 이렇게... ㅠㅠ
        KeyboardUtil.hideKeyboard(this);
        if ((user == null || !session.isLogin()) && isMainTabActivity) {
            loginTypeInit();        // 메인 탭 내의 프로필 액티비티이고, 로그인이 필요할 경우
        } else {    
            isMyPage = 
                session.isLogin()
                && session.getCurrentUser().getId() == user.getId();
            if (isMyPage) {
                privateTypeInit();  // 자기 자신의 프로필 액티비티일 경우
            } else {
                publicTypeInit();   // 이외의 유저의 프로필 액티비티일 경우
            }
        }
    }
    
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        syncTab();
    }
    
    @Override
    protected void onNotFlowResume() {
        // TODO Auto-generated method stub
        super.onNotFlowResume();
        refresh();
    }

    protected void loginTypeInit() {
    	tabHost.setCurrentTab(0);
        tabHost.clearAllTabs();
        getTabWidget().setVisibility(View.GONE);
        loginView.setVisibility(View.VISIBLE);
    }

    
    protected void publicTypeInit() {
        getTabWidget().setVisibility(View.VISIBLE);
        loginView.setVisibility(View.GONE);
        tabHost.setCurrentTab(0);
        tabHost.clearAllTabs();
        tabHost.addLeftTab(
                "tab1",
                R.string.default_string_info,
                profileIntent);
        tabHost.addRightTab(
                "tab2",
                R.string.default_string_tag,
                tagIntent);

    }

    protected void privateTypeInit() {
        getTabWidget().setVisibility(View.VISIBLE);
        loginView.setVisibility(View.GONE);
        tabHost.setCurrentTab(0);
        tabHost.clearAllTabs();
        tabHost.addLeftTab(
                "tab1",
                R.string.private_tab_my_info,
                profileIntent);
         tabHost.addRightTab(
                 "tab2",
                 R.string.private_tab_my_tag,
                 tagIntent);
    }

    public void refresh() {
    	
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(UserMainActivity.USER, (Parcelable) user);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    public void loginButtonClicked(View v) {        
        loginView.login(this);
    }

    /* Loginable Interface methods */
    public void loginCompleted() {
        setUser(session.getCurrentUser());
        loginView.clearField();
        syncTab();
    }

    public void loginFailed() {
        // show toast -> id, pw 확인해라
    }    
}