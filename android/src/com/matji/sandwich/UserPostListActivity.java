package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.UserPostListView;

import android.content.Intent;
import android.os.Bundle;

public class UserPostListActivity extends BaseActivity {
    private User user;		
    private UserPostListView listView;

    public static final String USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

        user = getIntent().getParcelableExtra(USER);

        listView = (UserPostListView) findViewById(R.id.user_post_list_view);
        listView.setUser(user);
        listView.setActivity(this);
        listView.requestReload();
    }
    @Override
    protected void onResume() {
        super.onResume();
        listView.dataRefresh();
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