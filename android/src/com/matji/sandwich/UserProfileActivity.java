package com.matji.sandwich;

import android.os.Bundle;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.Session.LoginListener;
import com.matji.sandwich.widget.UserProfileView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.title.UserTitle;

public class UserProfileActivity extends BaseActivity implements LoginListener {
    private boolean isMainTabActivity;

    private UserTitle title;
    private UserCell userCell;
    private UserProfileView userProfileView;
    
    private Session session;

    public static final String USER = "UserProfileActivity.user";
    public static final String IS_MAIN_TAB_ACTIVITY = "UserProfileActivity.is_main_tab_activity";

    public int setMainViewId() {
        return R.id.activity_user_profile;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        setContentView(R.layout.activity_user_profile);

        session = Session.getInstance(this);
        session.addLoginListener(this);
        isMainTabActivity = getIntent().getBooleanExtra(IS_MAIN_TAB_ACTIVITY, false);

        title = (UserTitle) findViewById(R.id.Titlebar);
        userCell = (UserCell) findViewById(R.id.UserCell);
        userProfileView = (UserProfileView) findViewById(R.id.user_profile_view);

        if (!isMainTabActivity) {
            title.setIdentifiable(this);
            title.setUser(UserProfileTabActivity.user);
            title.setFollowable(userCell);
        }

        userCell.setUser(UserProfileTabActivity.user);
        userCell.setIdentifiable(this);
        userCell.addRefreshable(userProfileView);
        if (!isMainTabActivity) userCell.addRefreshable(title);
        else dismissTitle();
    }
    
    public void showTitle() {
        title.setVisibility(View.VISIBLE);
    }

    public void dismissTitle() {
        title.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userCell.refresh();
    }

    @Override
    public void preLogin() {}

    @Override
    public void postLogin() {
        if (isMainTabActivity) {
            userCell.setUser(session.getCurrentUser());
            userCell.refresh();
        }
    }
}