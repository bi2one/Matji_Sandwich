package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.UserPostListView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.cell.UserIntroCell;
import com.matji.sandwich.widget.title.UserTitle;

public class UserPostListActivity extends BaseActivity implements Refreshable {
    private User user;		
    private UserTitle title;
    private UserPostListView listView;
    private UserCell userCell;
    private UserIntroCell userIntroCell;

    public static final String USER = "user";

    public int setMainViewId() {
        return R.id.activity_user_post;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

        user = getIntent().getParcelableExtra(USER);

        title = (UserTitle) findViewById(R.id.Titlebar);
        userCell = new UserCell(this, user);
        userIntroCell = new UserIntroCell(this, user);

        title.setIdentifiable(this);
        title.setUser(user);
        title.setFollowable(userCell);

        userCell.setIdentifiable(this);
        userCell.addRefreshable(this);
        userCell.addRefreshable(title);
        userCell.showLine();

        listView = (UserPostListView) findViewById(R.id.user_post_list_view);
        listView.addHeaderView(userCell);
        listView.addHeaderView(userIntroCell);
        listView.setUser(user);
        listView.setActivity(this);
        listView.requestReload();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        listView.dataRefresh();
        userCell.refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case POST_MAIN_ACTIVITY:
            if (resultCode == RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostMainActivity.POSTS);
                listView.setPosts(posts);
                setIsFlow(true);
            }
            break;
        case STORE_MAIN_ACTIVITY: case USER_MAIN_ACTIVITY: case IMAGE_SLIDER_ACTIVITY:            
            if (resultCode == Activity.RESULT_OK) {
                setIsFlow(true);
            }
            break;
        }
    }
    
    @Override
    public void setIsFlow(boolean isFlow) {
        if (getParent() instanceof BaseTabActivity) {
            ((BaseTabActivity) getParent()).setIsFlow(true);
        }
        super.setIsFlow(isFlow);
    }

    @Override
    public void refresh() {}

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof User){
            this.user = (User) data;
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
}