package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.UserPostListView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.cell.UserIntroCell;
import com.matji.sandwich.widget.title.UserTitle;

public class UserPostListActivity extends BaseActivity {
    private User user;		
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
        userCell = new UserCell(this, user);
        userCell.showLine();
        userIntroCell = new UserIntroCell(this, user);

        ((UserTitle) findViewById(R.id.Titlebar)).setUser(user);
        ((UserTitle) findViewById(R.id.Titlebar)).setFollowable(userCell);
        
        listView = (UserPostListView) findViewById(R.id.user_post_list_view);
        listView.setUser(user);
        listView.setActivity(this);
        listView.requestReload();
        listView.addHeaderView(userCell);
        listView.addHeaderView(userIntroCell);
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
        case POST_ACTIVITY:
            if (resultCode == RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostActivity.POSTS);
                listView.setPosts(posts);
            }
            break;
        }
    }
}