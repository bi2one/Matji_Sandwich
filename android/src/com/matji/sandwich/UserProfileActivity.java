package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.Session.LoginListener;
import com.matji.sandwich.widget.UserProfileView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.title.UserTitle;

public class UserProfileActivity extends BaseActivity implements LoginListener, Requestable {
    private boolean isMainTabActivity;

    private UserTitle title;
    private UserCell userCell;
    private UserProfileView userProfileView;

    private Session session;

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
            title.setUser(getUser());
            title.setFollowable(userCell);
            title.setTitle(R.string.title_user_info);
        }

        userCell.setUser(getUser());
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
        if (isMainTabActivity && session.isLogin()) reload();
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

    @Override
    public void setIsFlow(boolean isFlow) {
        super.setIsFlow(isFlow);

        if (getParent() instanceof BaseTabActivity) {
            ((BaseTabActivity) getParent()).setIsFlow(true);
        }
    }

    public void reload() {
        UserHttpRequest request = new UserHttpRequest(this);
        request.actionShow(session.getCurrentUser().getId());
        HttpRequestManager.getInstance().request(this, request, HttpRequestManager.USER_SHOW_REQUEST, this);
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequestManager.USER_SHOW_REQUEST:
            User user = (User) data.get(0);
            session.setCurrentUser(user);
            userCell.refresh();
            break;
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_reload:
            reload();
            return true;
        }
        return false;
    }
    
    
    public User getUser() {
        return isMainTabActivity ? UserProfileMainTabActivity.user : UserProfileTabActivity.user;
    }
}